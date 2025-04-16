# Genius 🎵


<div align="center">
  <img src="https://theme.zdassets.com/theme_assets/2020272/b2406274743eea64df249dd553b47fb67d07272b.png" width="100%">

</div>
<br />


### **📌Description**  
---
**Genius** is a popular platform where music enthusiasts can explore and annotate the meaning behind song lyrics and other media.
This project is a simple Java-based implementation inspired by the Genius concept. It demonstrates core **object-oriented programming (OOP)** principles such as encapsulation, inheritance, and polymorphism to model and manage lyrical content and user interactions.

### **Features**
 -User Roles and Permissions
 -Commenting & Like tracks
 -Follow artist
 -Search & Browse Functionality
  
### **🛠️Requirements**
---
Ensure the following are installed before running the application:
* **Java 23** (Required for compilation and execution)
* **Gradle** (For dependency management)
* **Git** (For version control)

### **📦Installation**
---
1.Clone this repository to your local machine.
2.Install required dependencies using Gradle.

### **✨Key Features**
---
- **User Roles**:  
  - **Regular Users**: Search songs, view lyrics, follow artists, like/comment.  
  - **Artists**: Upload/edit songs, manage albums, track followers.  
  - **Admins**: Approve artist accounts, view all users.  
- **Music Discovery**: Search by title, artist, or lyrics.  
- **Social Features**: Follow artists, like songs, add comments.  
- **Analytics**: Track song views, likes, and popularity rankings.  

## 🧰 Detailed Component Breakdown



### 👥 User Management
| Component | Inheritance | Key Features | Relationships | Methods | 
|-----------|-------------|--------------|---------------|---------|
| **`Account`** | Abstract Base | <ul><li>Base user properties</li><li>Password verification</li></ul> | Parent of all user types | `verifyPassword()` 
| **`User`** | Concrete | <ul><li>Follow artists</li><li>Like songs</li></ul> | Follows `Artist` | `followArtist()`, `likeSong()` 
| **`Artist`** | Concrete | <ul><li>Manage songs</li><li>Create albums</li></ul> | Owns `Song`/`Album` | `addSong()`, `createAlbum()` 
| **`Admin`** | Concrete | <ul><li>Approve artists</li><li>View users</li></ul> | Manages `Artist` | `approveArtist()`, `viewAllUsers()` 

### 🎵 Music Core
| Component | Data Structure | Key Attributes | Relationships | Important Methods | 
|-----------|----------------|----------------|---------------|--------------------|
| **`Song`** | Class | <ul><li>`title`</li><li>`lyrics`</li><li>`artists`</li></ul> | Belongs to `Artist`/`Album` | `likeSong()`, `addComment()` 
| **`Album`** | Class | <ul><li>`title`</li><li>`tracklist`</li></ul> | Contains `Song` | `addSong()`, `getTracklist()` 
| **`Comment`** | Class | <ul><li>`content`</li><li>`user`</li></ul> | Linked to `User`+`Song` | `getContent()`, `getUser()` 

### **✍🏻Author**
 **Nazanin Zahra Fatemi**

## **📜 License**
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)  
