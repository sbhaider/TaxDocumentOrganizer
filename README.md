S. Haider 6061797
SE2070 Final project

TaxDocumentOrganizer System
is a console-based Java application that helps users track, organize, and verify tax documents for a given tax year. The goal of the project is to provide a simple way to define which tax documents are expected, record which documents have been received, and generate a report showing which documents are still missing.  the application automatically saves data to a file and loads it when the program starts, so the user's infomration is preserved between session. 

Features:  (8-10 ) User interactions 
1. Define Expected documents  - The user can define a list of expected documents for a specific tax year.   The program includes a built-in list of common IRS tax forms ( sucah as W-2, 1099-INT, 1095-A, etc).  The program will also allow a user to add custom documents in case the option you need to add is not on the list. 

2. Add Document - The user can add a document by entering the document type, tax year and source.  The program also asks whether the document has been received or not and stores this data.  This way the user can track if they have the document or have not received it yet. 

3. Automatic Input normalization - The program normalizes common variations of document types.  For example, inputs like "w2", "W2" or "w-2" are treated as "W-2".  similar normalization is applied to forms like 1095-A and 1099-INT.  this makes the system more forgiving and easier to use. 

4. Automatic Saving and Loading  - For this version of the program, all data is automatically saved to a file named taxdata.txt whenver changes are made.  When the program starts, it automatically loads existing data from tis file if it exists. 

5. List All Documents  - The user can list all stored documents.  Each document shows its type, tax year, and source.  It also shows whether the document has been received or not received. 

6. Search Documents - The user can search for documents by tax year, by document type, or by keyword.  This makes it easier to find specific records. 

7. Delete Document - The user can delete a document by selecting it from the using its number. 

8. Generate Missing Document Report - The user can generate a report for a specific tax year that compares expected documents to received documents.  The report lists any documents that are still missing. Built-in IRS forms include desciptions, and custom forms are labeled as "Custom Document".

9. Input Validation - The program validates menu choices to prevent crashes from invalid input and prompts the   user to enter a valid option. 

**How To Run**
Step 1: Ensure that Java 21 is installed on your computer. 
Step 2: Save the project files 
    Make sure all required files are saved inthe same folder.  The files for this project are:
        Main.java
        DocumentManager.java 
        TaxDocument.java 
        README.md
     All files should b e located inside the local folder named TaxDocumentOrganizer, with the .java files inside the src folder. 
Step 3: Open the project in Visual Studio Code
    Open Visual Studio Code. ( or terminal) 
    Go to file and open the folder TaxDocumentOrganizer (where the files should be saved)
Step 4: Commpile the program
    Navigate to the project folder and type javac src/*.java
Step 5: Run the program
    After successfully compiling the program run the program by typing java - cp src Main 
    or by pressing the icon in the upper right corner of VS code to run it. 
Step 6: Use the Menu
    The main menu for the program will appear in the terminal 
    Enter the number for the option you want to use. 
    Follow the prompts to define expected documents, add documents, search, delete, or generate reports. 
    
