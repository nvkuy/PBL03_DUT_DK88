use DK88;

CREATE TABLE NUser 
(
	userName VARCHAR(100) PRIMARY KEY,
	passWord VARCHAR(100),
	name NVARCHAR(100),
	phoneNumber VARCHAR(20),
	roleCode INT
);

CREATE TABLE Student 
(
	userName VARCHAR(100) PRIMARY KEY FOREIGN KEY REFERENCES NUser(userName),
	studentID VARCHAR(20) UNIQUE NOT NULL,
	facebook VARCHAR(100),
	status INT
);

CREATE TABLE QueryClass (
	idQuery INT IDENTITY(1, 1) PRIMARY KEY,
	targetID VARCHAR(20) UNIQUE FOREIGN KEY REFERENCES Student(studentID)
);

CREATE TABLE StudentClass (
	studentID VARCHAR(20) FOREIGN KEY REFERENCES QueryClass(targetID),
	classID VARCHAR(33),
	have BIT,
	CONSTRAINT PK_SC PRIMARY KEY (studentID, classID)
);

CREATE TABLE Admin 
(
	userName VARCHAR(100) PRIMARY KEY FOREIGN KEY REFERENCES NUser(userName),
	email VARCHAR(100)
);

CREATE TABLE Request (
	requestID INT IDENTITY(1, 1) PRIMARY KEY,
	targetID VARCHAR(20) FOREIGN KEY REFERENCES Student(studentID),
	requestCode INT
);

CREATE TABLE BanRequest (
	requestID INT PRIMARY KEY FOREIGN KEY REFERENCES Request(requestID),
	moreDetail NVARCHAR(1000)
);

CREATE TABLE Image (
	fileName VARCHAR(100) PRIMARY KEY,
	owner VARCHAR(100) FOREIGN KEY REFERENCES NUser(userName)
);

CREATE TABLE ImageRequest (
	requestID INT FOREIGN KEY REFERENCES BanRequest(requestID),
	fileName VARCHAR(100) FOREIGN KEY REFERENCES Image(fileName),
	CONSTRAINT PK_IP PRIMARY KEY (requestID, fileName)
);

CREATE TABLE ActiveRequest (
	requestID INT PRIMARY KEY FOREIGN KEY REFERENCES Request(requestID),
	imageFront VARCHAR(100) FOREIGN KEY REFERENCES Image(fileName),
	imageBack VARCHAR(100) FOREIGN KEY REFERENCES Image(fileName),
);

CREATE TABLE NGroup (
	groupID VARCHAR(100) PRIMARY KEY,
	status INT
);

CREATE TABLE GroupStudent (
	groupID VARCHAR(100) FOREIGN KEY REFERENCES NGroup(groupID),
	studentID VARCHAR(20) FOREIGN KEY REFERENCES Student(studentID),	
	vote BIT,
	CONSTRAINT PK_GS PRIMARY KEY(groupID, studentID)
);

CREATE TABLE GroupStudentClass (
	groupID VARCHAR(100) FOREIGN KEY REFERENCES NGroup(groupID),
	studentID VARCHAR(20) FOREIGN KEY REFERENCES Student(studentID),
	classID VARCHAR(100) NOT NULL,
	CONSTRAINT PK_GSC PRIMARY KEY(groupID, studentID, classID)
);

CREATE INDEX IDX_STUDENT_CLASS ON GroupStudentClass(studentID, classID);

CREATE TABLE StudentInGroup (
	studentID VARCHAR(20) PRIMARY KEY FOREIGN KEY REFERENCES Student(studentID),
	groupID VARCHAR(100) FOREIGN KEY REFERENCES NGroup(groupID),
);

