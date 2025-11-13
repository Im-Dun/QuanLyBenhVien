# Upload Hospital Management Project to GitHub

## Step 1: Install Git
Download and install Git from: https://git-scm.com/download

## Step 2: Create a GitHub Account
- Go to https://github.com
- Sign up for a free account

## Step 3: Create a New Repository on GitHub
1. Click the "+" icon in top right corner → "New repository"
2. Repository name: `HospitalManagement` (or your preferred name)
3. Description: "Hospital Management System - Patient, Doctor, Appointment Management"
4. Choose "Public" or "Private"
5. **Do NOT** check "Initialize this repository with a README"
6. Click "Create repository"

## Step 4: Open Command Prompt/Terminal
- Navigate to your project folder:
  ```
  cd d:\hoctap\Java\HospitalManagement
  ```

## Step 5: Initialize Git Repository
```bash
git init
```
This creates a `.git` folder (hidden) in your project.

## Step 6: Configure Git (First time only)
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

## Step 7: Add All Files to Staging Area
```bash
git add .
```
The dot (.) means add all files.

## Step 8: Create First Commit
```bash
git commit -m "Initial commit: Hospital Management System"
```

## Step 9: Add Remote Repository URL
Replace `YOUR_USERNAME` and `REPO_NAME` with your actual GitHub username and repository name:
```bash
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/REPO_NAME.git
```

Example:
```bash
git remote add origin https://github.com/johndoe/HospitalManagement.git
```

## Step 10: Push to GitHub
```bash
git push -u origin main
```

You'll be prompted to enter your GitHub credentials:
- Username: Your GitHub username
- Password: Your GitHub personal access token (not your password)
  - Create token: GitHub Settings → Developer settings → Personal access tokens → Tokens (classic) → Generate new token
  - Give it "repo" permission

## Step 11: Verify on GitHub
- Go to https://github.com/YOUR_USERNAME/REPO_NAME
- You should see all your files uploaded!

---

## Common Commands for Future Updates

### After making changes to your code:
```bash
git add .
git commit -m "Description of changes"
git push
```

### View commit history:
```bash
git log
```

### Check status:
```bash
git status
```

### Create a .gitignore file (recommended)
Create a file named `.gitignore` in your project root:
