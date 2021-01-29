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
    "roleCode": ""
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
- Endpoint : `/student/register`
- Body :

```json
{
  "id": "",
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
    "photo": "",
    "firstName": "",
    "lastName": "",
    "email": "",
    "phoneNumber": "",
    "gender": "",
    "createdAt": ""
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
- Endpoint: `exam/avarage-scores/student/:id`

#### Response:

success

```json
{
  "code": 200,
  "result": {
    "grade": "",
    "code": "",
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
      "courseCode": "",
      "courseName": "",
      "capacity": "",
      "periodStart": "",
      "periodEnd": "",
      "teacher": {
        "id": "",
        "code": "",
        "firstName": "",
        "lastName": "",
        "tittle": "",
        "photoId": ""
      },
      "courseCategory": ""
    }
  ]
}
```

### DETAIL COURSE

#### Request :

- Method : `GET`
- Endpoint : `/modules/course/:id`

#### Response :

success

```json
{
  "code": 200,
  "result": {
    "id": "",
    "code": "",
    "name": "",
    "modules": [
      {
        "id": "",
        "code": "",
        "tittle": "",
        "description": "",
        "subjectName": "",
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

### MY COURSE

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
      "name": "",
      "capacity": "",
      "periodStart": "",
      "periodEnd": "",
      "category": "",
      "teacher": {
        "id": "",
        "code": "",
        "firstName": "",
        "lastName": "",
        "tittle": ""
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
- Endpoint : `/student/attendance`
- Body:

```json
{
  "moduleId": "",
  "studentId": ""
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

### Verify attendance

#### Request :

- Method : `PATCH`
- Endpoint : `/student/attendance`
- Body:

```json
{
  "attendanceId": "",
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
    "files": [
      {
        "id": "",
        "name": "",
        "extensions": ""
      }
    ],
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
      "code": "",
      "description": "",
      "type": "",
      "start_time": "",
      "end_time": "",
      "file": [
        {
          "id": "",
          "name": "",
          "extensions": ""
        }
      ]
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
- Endpoint: `exam/student/`
- Body:

```json
{
  "examId": "",
  "studentId": "",
  "file": {
    "id": "",
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
  "moduleId": "",
  "description": "",
  "startTime": "",
  "endTime": "",
  "files": [
    {
      "id": "",
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
      "message_time": "",
      "user": {
        "id": "",
        "firstName": "",
        "lastName": "",
        "roleCode": "",
        "photo_id": ""
      }
    }
  ]
}
```

#### Request:

- Method: `POST`
- Endpoint: `forum/module/:id`
- body:

```json
{
  "content": "lorem",
  "userId": ""
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
    "student": 0,
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
      "firstName": "",
      "lastName": "",
      "email": "",
      "phone": "",
      "gender": ""
    }
  ]
}
```

### Get Exam Submission

#### Request

- Method: `GET`,
- Endpoint: `exam/:id/submission`

#### Response

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
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

- Method: `PATCH`
- Endpoint: `exam/submission`
- Body:

```json
{
  "submissionId": "",
  "score": ""
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
      "firstName": "",
      "lastName": "",
      "email": "",
      "title": "",
      "createdAt": "",
      "gender": "",
      "address": ""
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
- Endpoint: `teacher/:id`
- Body:

```json
{
  "teacher": {
    "firstName": "",
    "lastName": "",
    "email": "",
    "title": "",
    "createdAt": "",
    "gender": "",
    "address": ""
  },
  "experiences": [
    {
      "id": "",
      "title": "",
      "description": "",
      "startDate": "",
      "endDate": ""
    }
  ],
  "userId": ""
}
```

### GET Student Profile

#### Request

- Method: `GET`
- Endpoint: `student/:id`

#### Response

```json
{
  "code": "",
  "result": {
    "firstName": "",
    "lastName": "",
    "email": "",
    "title": "",
    "createdAt": "",
    "gender": "",
    "address": ""
  }
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
    "firstName": "",
    "lastName": "",
    "email": "",
    "title": "",
    "createdAt": "",
    "gender": "",
    "address": ""
  }
}
```

### List Teacher

#### Request

- Method: `GET`
- Endpoint: `admin/teachers`

### Response

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "first_name": "",
      "last_name": "",
      "phone": "",
      "gender": "",
      "username": ""
    }
  ]
}
```

### REGISTER TEACHER

#### Request :

- Method : `POST`
- Endpoint : `/admin/teacher/register`
- Body :

```json
{
  "code": "",
  "first_name": "",
  "last_name": "",
  "phone": "",
  "gender": "",
  "username": "",
  "password": "",
  "email": "",
  "role_id": ""
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
- Endpoint: `admin/teacher/:id`
- Body:

```json
{
  "id": "",
  "firstName": "",
  "lastName": "",
  "email": "",
  "title": "",
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
- Endpoint: `admin/teacher/:id`

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
- Endpoint : `/admin/courses`

#### Response :

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "courseCode": "",
      "courseName": "",
      "typeName": "",
      "capacity": "",
      "periodStart": "",
      "status": "",
      "periodEnd": "",
      "courseCategory": "",
      "description": ""
    }
  ]
}
```

### Create Course

#### Request :

- Method : `POST`
- Endpoint : `/admin/course`
- Body:

```json
{
  "courseCode": "",
  "courseName": "",
  "typeName": "",
  "capacity": "",
  "periodStart": "",
  "status": "",
  "periodEnd": "",
  "courseCategory": "",
  "description": "",
  "teacherId": ""
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
- EndPoint : `/admin/course/:id`

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
- Endpoint : `/admin/subject-categories`

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
      "subjectName": ""
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

### Update Categories

#### Request :

- Method : `PUT`
- Endpoint : `/admin/subject-categories`
- Body:

```json
{
  "code": "",
  "description": "",
  "subjectName": ""
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

### Add Categories

#### Request :

- method : `POST`
- Endpoint : `/admin/subject-categories`
- Body:

```json
{
  "code": "",
  "description": "",
  "subjectName": ""
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
