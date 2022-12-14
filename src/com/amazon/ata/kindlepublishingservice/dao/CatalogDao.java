package com.amazon.ata.kindlepublishingservice.dao;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;
import com.amazon.ata.kindlepublishingservice.publishing.KindleFormattedBook;
import com.amazon.ata.kindlepublishingservice.utils.KindlePublishingUtils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;

import java.util.List;
import javax.inject.Inject;

public class CatalogDao {

    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new CatalogDao object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the catalog table.
     */
    @Inject
    public CatalogDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the latest version of the book from the catalog corresponding to the specified book id.
     * Throws a BookNotFoundException if the latest version is not active or no version is found.
     * @param bookId Id associated with the book.
     * @return The corresponding CatalogItem from the catalog table.
     */
    public CatalogItemVersion getBookFromCatalog(String bookId) {
        CatalogItemVersion book = getLatestVersionOfBook(bookId);

        if (book == null || book.isInactive()) {
            throw new BookNotFoundException(String.format("No book found for id: %s", bookId));
        }

        return book;
    }

    // Returns null if no version exists for the provided bookId
    private CatalogItemVersion getLatestVersionOfBook(String bookId) {
        CatalogItemVersion book = new CatalogItemVersion();
        book.setBookId(bookId);

        DynamoDBQueryExpression<CatalogItemVersion> queryExpression = new DynamoDBQueryExpression()
            .withHashKeyValues(book)
            .withScanIndexForward(false)
            .withLimit(1);

        List<CatalogItemVersion> results = dynamoDbMapper.query(CatalogItemVersion.class, queryExpression);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public CatalogItemVersion softDeleteBookFromCatalog(String bookId) {
       CatalogItemVersion bookEntry = getBookFromCatalog(bookId);
       bookEntry.setInactive(true);

        dynamoDbMapper.save(bookEntry);
        return bookEntry;
    }
    public CatalogItemVersion validateBookExists(String bookId) {
        CatalogItemVersion catalogItemVersion = getLatestVersionOfBook(bookId);
        if (catalogItemVersion == null) {
            throw new BookNotFoundException("book is not in the catalog :" + bookId );
        }
        return catalogItemVersion;
    }

    public CatalogItemVersion createOrUpdateBook(KindleFormattedBook kindleFormattedBook) {
        CatalogItemVersion createCatalogItemVersion = new CatalogItemVersion();
        createCatalogItemVersion.setBookId(kindleFormattedBook.getBookId());

        if (createCatalogItemVersion.getBookId() == null) {
            createCatalogItemVersion.setBookId(KindlePublishingUtils.generateBookId());
            createCatalogItemVersion.setAuthor(kindleFormattedBook.getAuthor());
            createCatalogItemVersion.setGenre(kindleFormattedBook.getGenre());
            createCatalogItemVersion.setText(kindleFormattedBook.getText());
            createCatalogItemVersion.setTitle(kindleFormattedBook.getTitle());
            createCatalogItemVersion.setVersion(1);
            dynamoDbMapper.save(createCatalogItemVersion);

        } else {
            createCatalogItemVersion = getLatestVersionOfBook(kindleFormattedBook.getBookId());
            CatalogItemVersion oldEntry = validateBookExists(kindleFormattedBook.getBookId());
            softDeleteBookFromCatalog(oldEntry.getBookId());
            createCatalogItemVersion.setVersion(createCatalogItemVersion.getVersion() + 1);
            dynamoDbMapper.save(createCatalogItemVersion);
        }





     return  createCatalogItemVersion;
    }
}
