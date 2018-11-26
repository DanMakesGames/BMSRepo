# Budgeting Made Simple Repo

### How to use:
For now read https://nvie.com/posts/a-successful-git-branching-model/
Basically, make a branch off of development when you want to work on a feature, then merge it back into development when you are done.

To make a new branch: 
####git checkout -b newBranchName
To merge your branch back into development: 
####git checkout development
####git merge --no-ff newBranchName
####git push origin develop

// you can delete the old branch
git branch -d newBranchName

### SQL Database
#### General Information
The application uses a local SQLite database to store data.
If we were to ever release our application to end users, we would refactor our code to ensure that the database resided on a remote server.
#### For Developers
Contained in `MainActivity.java::showcaseDatabase()` you will find example code of how to interact with the SQL database.
