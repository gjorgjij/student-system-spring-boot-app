# student-system-spring-boot-app

# How to run
1. Clone the repository
2. Open project with your IDE
3. Setup connectivity with your DB(MySQL)
3. Run App.java
4. Open Postman and run:


**Add a Student**
  - POST Body req[name, email]:

```
http://localhost:8080/demo/students/add
```
**List Students**
  - GET

```
http://localhost:8080/demo/students/all
```
**Add a Course**
  - POST Body req[name]:

```
http://localhost:8080/demo/courses/add
```
**List Courses**
  - GET Headers[hash]:

```
http://localhost:8080/demo/courses/all
```
**Get Course by name**
  - GET Headers[hash], replace "History" with course name:

```
http://localhost:8080/demo/courses/History
```
**Enroll in**
  - POST Headers[hash], Body[student_id, course_id]:

```
http://localhost:8080/demo/enrollments/add
```
**Cancel enrollment**
  - POST Headers[hash], Body[student_id, course_id]:

```
http://localhost:8080/demo/enrollments/cancel
```
**List enrollments**
  - GET:

```
http://localhost:8080/demo/enrollments/all
```


### Note
Every Student contains its own unique hash, be aware that you need to pass the hash in the headers section of the request for these specific methods:
  - List all courses
  - Search course by name
  - Enroll in 
  - Cancell enrollment
