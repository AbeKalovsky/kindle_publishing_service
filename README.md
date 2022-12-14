# Unit 6 Project: Kindle Publishing Service

### Ambiguity, Complexity, and Scope

Ambiguity will be increasing from prior unit's projects as you make your way through the Unit 6 project
mastery tasks. We’ll assume you’ll be using the design document to understand the requirements and
how to implement each API, so the tasks will contain fewer details. After walking you through
something once, we won’t be mentioning it again in each subsequent task. For example, we used Dagger
in Unit 4, and will again in Unit 6. We’ll walk you through it a bit in mastery task 2, but then
you’ll be responsible for knowing that this is something you’ll need to think about and update in
each subsequent task.

We’ll also be increasing complexity. We'll be working with threads for the first time, and write a
new style of API to take advantage of them. We'll also be integrating caching into our project.

Scope will increase slightly. As in last unit, we'll be implementing the entire service, but now
you'll be responsible for writing the Activity classes as well.

You'll be surrounded by other participants in the same situation as you, so remember to collaborate:
rely on each other for assistance, and share your own knowledge.

## The Problem: Amazon Kindle Publishing

The Amazon Kindle store provides millions of ebooks to our customers. The process of publishing an
ebook to the kindle catalog is currently an extremely manual process, which causes a long wait time
to add a book to the catalog.

As a member of the Amazon Kindle team, you will be launching a new service that allows our
publishing department to convert books into a digital format.

The overview, architecture, and implementation are covered in the [design document here](DESIGN_DOCUMENT.md). Almost all major pieces of software at Amazon first go through an intensive design
review to answer the question "Are we building the right thing for our customer?".

By working on the preparedness and mastery tasks for this project, you will finish the
implementation of the service described in the document.

Carefully read the design document and refer back to it while working on the tasks.

## Project Preparedness Tasks

Up to this point, the services we have developed in projects have had synchronous APIs. This
means that a client makes a request to the service, all of the work required to fulfill this request
is done, and then a response is returned to the client. In an asynchronous API, a client makes a
request, the service returns a response immediately, and the service completes the work after the
client disconnects. This is helpful when the work that needs to be done will take a long amount of
time. A client will only wait so long for a response, so it is helpful to quickly return a
successful response acknowledging the work is under way. The service will then continue to work on
the request as it continues to receive other, new requests. The service is working on these requests
concurrently - we can think of this as multi-tasking for now. We’ll spend a lot more time in 
this unit and future units digging into the concept of concurrency much more deeply.

Examine the folders in the starter project to be sure you are familiar with their contents.  Everything you need to start the project and create/populate your DynamoDB tables has been provided.

#### Remember: U.P.E.R.

Note:  You may need to add ***Mock***s to some tests as you progress through the Mastery Tasks.

&nbsp;

## Project Mastery Tasks
#### Under each mastery task I have linked to parts of the project that are my code.  
### [Mastery Task 1: Killing me softly](tasks/MasteryTask01.md)

My Contributions:   
- [Class diagram](src/resources/mastery-task1-kindle-publishing-CD.puml)  
- [Sequence diagram](src/resources/mastery-task1-remove-book-SD.puml)  
- [RemoveBookActivity](https://github.com/AbeKalovsky/kindle_publishing_service/commit/5431a9b1009d76d2338516e3409f4cbe87655c89#diff-425953d684a72c3bf3cfd03b7640ef448c58a593f6132aa59de3fdd966212fc4)
- [SoftDelete](src/com/amazon/ata/kindlepublishingservice/dao/CatalogDao.java)  

### [Mastery Task 2: Submit to the process](tasks/MasteryTask02.md)

My Contributions:
- [BookPublishRequestManger](src/com/amazon/ata/kindlepublishingservice/publishing/BookPublishRequestManager.java)  
- [Catalog Dao](https://github.com/AbeKalovsky/kindle_publishing_service/commit/b2db2de9c5d7ca3c926771ce66c786c5b4622d20#diff-1e0d63a2b6ec3ceaca2b52a387c966bccffb587c829fa5528b1954afdb4b8845)
- [PublishingModule](https://github.com/AbeKalovsky/kindle_publishing_service/commit/b2db2de9c5d7ca3c926771ce66c786c5b4622d20#diff-6bc374721e69142088e9589ab90032e1bf375a94ffb5b42e26e14178a938c8bd)
- [SubmitBookForPublishingActivity](src/com/amazon/ata/kindlepublishingservice/activity/SubmitBookForPublishingActivity.java) 

### [Mastery Task 3: Query, query on the wall, don’t load one, get them all!](tasks/MasteryTask03.md)

My Contributions:
- [DynamoDB Query](src/com/amazon/ata/kindlepublishingservice/dao/PublishingStatusDao.java)
- [PublishingStatusItemConverter](https://github.com/AbeKalovsky/kindle_publishing_service/commit/de0a071cde19c7ba6c0e34c4f522144232a71534#diff-a28698e34b6a18c6505399fca3fba8b1820ee0eaf5b98c187abaae1583359cb0)
- [GetPublishingStatus](src/com/amazon/ata/kindlepublishingservice/activity/GetPublishingStatusActivity.java) 

### [Mastery Task 4: Make a run(nable) for it](tasks/MasteryTask04.md)

My Contributions:
- [BookPublishTask (Runnable)](src/com/amazon/ata/kindlepublishingservice/publishing/BookPublishTask.java)
- [BookPublishingRequestManager(Updated for Thread Safety)](src/com/amazon/ata/kindlepublishingservice/publishing/BookPublishRequestManager.java)
- [BookPublishRequest(Updated for Thread Safety)](src/com/amazon/ata/kindlepublishingservice/publishing/BookPublishRequest.java)



