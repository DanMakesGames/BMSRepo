# Budgeting Made Simple Repo

### How to use:
For now read https://nvie.com/posts/a-successful-git-branching-model/
Basically, make a branch off of development when you want to work on a feature, then merge it back into development when you are done.

To make a new branch: git checkout -b newBranchName
To merge your branch back into development: 
git checkout development
git merge --no-ff newBranchName
git push origin develop

// you can delete the old branch
git branch -d newBranchName

### Firebase
Check that you have all required dependencies and package verisions by watching first ~2 minutes of: 
https://www.youtube.com/watch?v=cNPCgJW8c-E&feature=youtu.be


