# E-learning Back End Application

Back end application for e-learning.

# API Spec

## Authentication

All API request must be use this authentication

Request:

- Header:
  - Authentication: "Bearer ```token```"

## STUDENT

### LOGIN

#### Request :

- Method : `POST`
- Endpoint : `/login`
- Body :

```json
{
  "username": "string",
  "password": "username"
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": {
    "token": "",
    "userId": "",
    "username": "",
    "role": {
      "id": "",
      "code": "",
      "name": "",
      "version": ""
    }
  }
}
```

error

```json
{
  "code": 403,
  "result": "message"
}
```

### Register

#### Request:

- Method : `POST`
- Endpoint : `/student`
- Body :

```json
{
  "firstName": "",
  "lastName": "",
  "username": "",
  "password": "",
  "email": "",
  "phone": "",
  "gender": ""
}
```

#### Response :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Dashboard Student

#### Request :

- Method : `GET`
- Endpoint : `/student/dashboard/:id`

### Response :

success`

```json
{
  "code": 200,
  "result": {
    "id": "",
    "code": "",
    "username": "",
    "firstName"; "",
    "lastName": "",
    "email": "",
    "phone": "",
    "gender": "",
    "idPhoto": "",
    "createdAt": "",
    "isActive": ""
  }
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Student's Score

- Method: `GET`
- Endpoint: `exam/average-scores/student/:id`

#### Response:

success

```json
{
  "code": 200,
  "result": {
    "code": "",
    "averageScore": "",
    "title": "",
    "startTime": "",
    "endTime": ""
  }
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

## COURSE

### Available Course

#### Request :

- Method : `GET`
- Endpoint : `/course/available`

#### Response :

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "typeName": "",
      "capacity": "",
      "periodStart": "",
      "periodEnd": "",
      "categoryName": "",
      "categoryCode": "",
      "teacher": {
        "id": "",
        "code": "",
        "firstName": "",
        "lastName": "",
        "title": "",
        "photoId": "",
        "experience": ""
      },
      "isRegist": "",
      "courseStatus": "",
      "courseDescription": "",
      "periodStart": "",
      "periodEnd": ""
    }
  ]
}
```

### DETAIL COURSE

#### Request :

- Method : `GET`
- Endpoint : `/course/:id?studentId=:studentId`

#### Response :

success

```json
{
  "code": 200,
  "result": {
    "id": "",
    "code": "",
    "name": "",
    "capacity": "",
    "totalStudent": "",
    "description": "",
    "periodStart": "",
    "periodEnd": "",
    "modules": [
      {
        "id": "",
        "code": "",
        "tittle": "",
        "description": "",
        "subjectName": "",
        "attendanceId": "",
        "verifyStatus": "",
        "schedule": {
          "id": "",
          "date": "",
          "start_time": "",
          "end_time": ""
        }
      }
    ]
  }
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### REGISTER COURSE

#### Request :

- Method : `POST`
- Endpoint : `/course/register`
- Body :

```json
{
  "studentId": "",
  "courseId": ""
}
```

#### Response :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### STUDENT COURSE

#### Request :

- Method : `GET`
- Endpoint : `/student/:id/course`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "typeName": "",
      "capacity": "",
      "isRegist": "",
      "courseStatus": "",
      "courseDescription": "",
      "periodStart": "",
      "periodEnd": "",
      "categoryName": "",
      "categoryCode": "",
      "teacher": {
        "id": "",
        "code": "",
        "firstName": "",
        "lastName": "",
        "tittle": "",
        "experience": "",
        "photoId": ""
      }
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

## ATTENDANCE

### Create attendance

#### Request :

- Method : `POST`
- Endpoint : `/attendance/student`
- Body:

```json
{
  "idStudent": "",
  "idModule": ""
}
```

#### Response :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Check attendance status

#### Request :

- Method : `GET`
- Endpoint : `/attendance/status?idModule=:idModule?idStudent=:idStudent`

#### Response :

success

```json
{
  "code": 200,
  "result": {
    "status": ""
  }
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Get List Attendance

#### Request :

- Method : `GET`
- Endpoint : `/attendance/status?idCourse=:idCourse?idModule=:idModule`

#### Response :

success

```json
{
  "code": 200,
  "result": [
	  {
	    "attendanceId": "",
	    "attendanceTime": "",
	    "attendanceVersion": "",
	    "attendanceIsVerified": ""
	  }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Verify attendance

#### Request :

- Method : `PATCH`
- Endpoint : `/attendance?id=:id?userId=:userId`
- Body:

```json
{
  "id": "",
  "userId": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

## MODULE

### Detail Module

#### Request:

- Method: `GET`
- Endpoint: `module/:id`

#### Response

success

```json
{
  "code": 200,
  "result": {
    "title": "",
    "code": "",
    "description": "",
    "schedule": {
      "date": "",
      "startTime": "",
      "endTime": ""
    }
  }
}
```

## EXAM

### DETAIL MODULE EXAM

#### Request:

- Method: `GET`
- ENDPOINT: `exam/module/:id`

#### Response:

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "title": "",
      "code": "",
      "description": "",
      "type": "",
      "start_time": "",
      "end_time": "",
      "fileId": "",
      "version": "",
      "fileName": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Send a student's exam

#### Request:

- Method: `POST`
- Endpoint: `exam/student?examId=:examId?studentId=:studentId`
- Body:

```json
{
  "file": {
    "data": ""
  }
}
```

#### Response

```json
{
  "code": 200,
  "result": "message"
}
```

### Send a teacher's exam

#### Request

- Method: `POST`
- Endpoint: `exam/module/`

```json
{
  "body": {
  	"moduleId": "",
    "title": "",
    "description": "",
    "startTime": "",
    "endTime": "",
    "createdBy": "",
    "type": ""
  },
  "files": [
    {
      "data": ""
    }
  ]
}
```

### DETAIL MODULE FORUM

#### Request:

- Method: `GET`
- Endpoint: `forum/module/:id`

#### Response:

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "content": "",
      "createdAt": "",
      "userId": "",
      "firstName": "",
      "lastName": "",
      "roleId": "",
      "roleCode": "",
      "photoId": ""
    }
  ]
}
```

#### Request:

- Method: `POST`
- Endpoint: `forum`
- body:

```json
{
  "content": "lorem",
  "userId": "",
  "moduleId": ""
}
```

#### Response:

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

## Teacher

### Dashboard

#### Request

- Method: `GET`
- Endpoint: `teacher/dashboard/:id`

#### Response

```json
{
  "code": 200,
  "result": {
    "id": "",
    "code": "",
    "name": "",
    "description": "",
    "capacity": "",
    "totalStudent": "",
    "totalModule": "",
    "periodStart": "",
    "periodEnd": ""
  }
}
```

### Get Student Who Take The Course

#### Request

- Method: `GET`
- Endpoint: `course/:id/students`

#### Response

```json
{
  "code": "",
  "result": [
    {
      "id": "",
      "code": "",
      "typeName": "",
      "capacity": "",
      "isRegist": "",
      "courseStatus": "",
      "courseDescription": "",
      "periodStart": "",
      "periodEnd": "",
      "categoryName": "",
      "categoryCode": "",
      "teacher": {
        "id": "",
        "code": "",
        "firstName": "",
        "lastName": "",
        "title": "",
        "experience": "",
        "photoId": ""
      }
      
    }
  ]
}
```

### Get Exam Submission

#### Request

- Method: `GET`,
- Endpoint: `exam/:examId/submission/:studentId`

#### Response

```json
{
  "code": 200,
  "result": [
    {
      "detailId": "",
      "fileId": "",
      "fileName": "",
      "code": "",
      "firstName": "",
      "lastName": "",
      "grade": "",
      "submittedDate": ""
    }
  ]
}
```

### Update Score

#### Request

- Method: `PUT`
- Endpoint: `exam/submission`
- Body:

```json
{
  "id": "",
  "grade": "",
  "updatedBy": ""
}
```

#### Response

```json
{
  "code": 200,
  "result": "message"
}
```

### GET Teacher Profile

- Method: `GET`
- Endpoint: `teacher/:id`

#### Response

```json
{
  "code": "",
  "result": {
    "teacher": {
      "id": "",
      "username": "",
      "firstName": "",
      "lastName": "",
      "email": "",
      "titleDegree": "",
      "createdAt": "",
      "gender": "",
      "phone": "",
      "photoId": ""
    },
    "experiences": [
      {
        "id": "",
        "title": "",
        "description": "",
        "startDate": "",
        "endDate": ""
      }
    ]
  }
}
```

### PUT Teacher Profile

#### Request

- Method: `PUT`
- Endpoint: `teacher`
- Body:

```json
{
   "id": "",
   "firstName": "",
   "lastName": "",
   "phone": "",
   "titleDegree": "",
   "updatedBy": "",
   "gender": ""
}
```

### PUT Student Profile

#### Request

- Method: `PUT`
- Endpoint: `student/:id`

#### Response

```json
{
  "code": "",
  "result": {
    "id": "",
    "firstName": "",
    "lastName": "",
    "username": "",
    "phone": "",
    "updatedBy": "",
    "gender": ""
  }
}
```

### List Teacher

#### Request

- Method: `GET`
- Endpoint: `teacher/all`

### Response

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "username": "",
      "firstName": "",
      "lastName": "",
      "titleDegree": "",
      "email": "",
      "phone": "",
      "gender": "",
      "photoId": "",
      "isActive": ""
    }
  ]
}
```

### REGISTER TEACHER

#### Request :

- Method : `POST`
- Endpoint : `/teacher`
- Body :

```json
{
  "code": "",
  "firstName": "",
  "lastName": "",
  "phone": "",
  "gender": "",
  "username": "",
  "password": "",
  "email": "",
  "createdBy": "",
  "titleDegree": ""
}
```

#### Response :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### PUT Teacher Profile

#### Request

- Method: `PUT`
- Endpoint: `teacher`
- Body:

```json
{
  "id": "",
  "firstName": "",
  "lastName": "",
  "phone": "",
  "titleDegree": "",
  "gender": "",
  "updatedBy": ""
}
```

Response :

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### DELETE Teacher Profile

#### Request

- Method: `DELETE`
- Endpoint: `teacher/id/:id`

#### Response

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 404,
  "result": "message"
}
```

### Get List Course

#### Request :

- Method : `GET`
- Endpoint : `/course/admin`

#### Response :

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "categoryName": "",
      "typeName": "",
      "capacity": "",
      "periodStart": "",
      "status": "",
      "periodEnd": "",
      "typeId": "",
      "description": "",
      "categoryId": "",
      "teacherId": ""
    }
  ]
}
```

### Create Course

#### Request :

- Method : `POST`
- Endpoint : `/course`
- Body:

```json
{
  "code": "",
  "description": "",
  "courseTypeId": "",
  "teacherId": "",
  "courseCategoryId": "",
  "capacity": "",
  "periodStart": "",
  "periodEnd": "",
  "createdBy": ""
}
```

#### RESPONSE :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Delete Course

#### Request :

- Method : `DELETE`
- EndPoint : `/course`

```json
{
	"id": "",
	"updateBy": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Subject Categories

#### Request

- Method : `GET`
- Endpoint : `/subjectcategory`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "description": "",
      "name": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Update Subject Category

#### Request :

- Method : `PUT`
- Endpoint : `/subjectcategory`
- Body:

```json
{ 
  "id": "",
  "code": "",
  "subjectName": "",
  "description": "",
  "updatedBy": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Add Subject Category

#### Request :

- method : `POST`
- Endpoint : `subjectcategory`
- Body:

```json
{
  "code": "",
  "description": "",
  "subjectName": "",
  "createdBy": "",
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Delete Subject Category

#### Request :

- method : `DELETE`
- Endpoint : `subjectcategory`
- Body:

```json
{
  "id": "",
  "updatedBy": "",
  "status": ""
}
```

#### Response :

success

```json
{
  "code": 203,
  "result": "message"
}
```

error

```json
{
  "code": 404,
  "result": "message"
}
```

### Get List Course Category

#### Request

- Method : `GET`
- Endpoint : `/course/category`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "version": "",
      "name": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Create Course Category

#### Request

- Method : `POST`
- Endpoint : `/course/category`
- Body:

```json
{
  "code": "",
  "name": "",
  "createdBy": "",
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Update Course Category

#### Request

- Method : `PUT`
- Endpoint : `/course/category`
- Body:

```json
{
  "code": "",
  "name": "",
  "createdBy": "",
  "id": "",
  "updatedBy": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Delete Course Category

#### Request

- Method : `DELETE`
- Endpoint : `/course/category/id/:id`

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 404,
  "result": "message"
}
```

### Get List Course Type

#### Request

- Method : `GET`
- Endpoint : `/course/type`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "version": "",
      "name": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Create Course Type

#### Request

- Method : `POST`
- Endpoint : `/course/type`
- Body:

```json
{
  "code": "",
  "name": "",
  "createdBy": "",
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Update Course Type

#### Request

- Method : `PUT`
- Endpoint : `/course/type`
- Body:

```json
{
  "code": "",
  "name": "",
  "id": "",
  "updatedBy": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Delete Course Type

#### Request

- Method : `DELETE`
- Endpoint : `/course/type/id/:id`

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 404,
  "result": "message"
}
```

### Create Experience

#### Request

- Method : `POST`
- Endpoint : `/experience`
- Body:

```json
{
  "title": "",
  "description": "",
  "startDate": "",
  "endDate": "",
  "teacherId": "",
  "userId": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Update Experience

#### Request

- Method : `PUT`
- Endpoint : `/experience`
- Body:

```json
{
  "title": "",
  "description": "",
  "startDate": "",
  "endDate": "",
  "teacherId": "",
  "userId": "",
  "id": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Delete Experience

#### Request

- Method : `DELETE`
- Endpoint : `/experience/:id`

#### Response :

success

```json
{
  "code": 203,
  "result": "message"
}
```

error

```json
{
  "code": 404,
  "result": "message"
}
```

### Get Module Discussion

#### Request

- Method : `GET`
- Endpoint : `/forum/module/:id`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "content": "",
      "createdAt": "",
      "userId": "",
      "firstName": "",
      "lastName": "",
      "roleId": "",
      "roleCode": "",
      "photoId": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Create Discussion

#### Request

- Method : `POST`
- Endpoint : `/forum`
- Body:

```json
{
  "content": "",
  "moduleId": "",
  "userId": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Delete Discussion

#### Request

- Method : `DELETE`
- Endpoint : `/forum/?id=:id?userId=:userId`

#### Response :

success

```json
{
  "code": 203,
  "result": "message"
}
```

error

```json
{
  "code": 404,
  "result": "message"
}
```

### Get Role List

#### Request

- Method : `GET`
- Endpoint : `/roles`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "name": "",
      "createdAt": "",
      "version": "",
      "updatedAt": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Get Role By Id

#### Request

- Method : `GET`
- Endpoint : `/role/id/:id`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "name": "",
      "createdAt": "",
      "version": "",
      "updatedAt": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Create Role

#### Request

- Method : `POST`
- Endpoint : `/role`
- Body:

```json
{
  "code": "",
  "name": "",
  "userId": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Update Role

#### Request

- Method : `PUT`
- Endpoint : `/role`
- Body:

```json
{
  "code": "",
  "name": "",
  "userId": "",
  "id": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Get Schedules By Teacher Id

#### Request

- Method : `GET`
- Endpoint : `/schedules/teacher/:id`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "version": "",
      "date": "",
      "startTime": "",
      "endTime": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

