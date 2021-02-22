# E-learning Back End Application

Back end application for e-learning.

# API Spec

## Authentication

All API request must be use this authentication

Request:

- Header:
  - Authentication: "Bearer ```token```"

## ADMIN

### Get Dashboard

#### Request :

- Method : `GET`
- Endpoint : `/admin/dashboard`

#### Response :

success

```json
{
    "code": 200,
    "result": {
        "course": {
            "registeredStudent": "",
            "registeredTeacher": "",
            "available": "",
            "expired": "",
            "active": "",
            "inactive": "",
            "total": ""
        },
        "student": {
            "verified": "",
            "active": "",
            "inactive": "",
            "male": "",
            "female": "",
            "total": "",
            "registeredToCourse": ""
        },
        "teacher": {
            "experienced": "",
            "active": "",
            "inactive": "",
            "male": "",
            "female": "",
            "total": ""
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

-----------------------------------------------------------

## Attendance

### Check Attendance Status

#### Request:

- Method : `GET`
- Endpoint : `/attendance/status`
- Query Param : `idModule/ idStudent`

#### Response :

success

```json
{
  "code": 200,
  "result": ""
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
- Endpoint : `/attendance`
- Query Param : `idCourse/ idModule`

### Response :

success`

```json
{
    "code": 200,
    "result": [
        {
            "id": "",
            "code": "",
            "firstName": "",
            "lastName": "",
            "email": "",
            "phone": "",
            "gender": "",
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

### Create Attendance

- Method: `POST`
- Endpoint: `attendance/student`
- Body :

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

### Verify Attendance

- Method: `PATCH`
- Endpoint: `attendance`
- Query Param : `attendanceId/userId`

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

-----------------------------------------------------------

## Course Category

### Get List Course Cateogry

#### Request :

- Method : `GET`
- Endpoint : `/course/category`

#### Response :

```json
{
    "code": 200,
    "result": [
        {
            "id": "",
            "code": ",
            "name": "",
            "version": ""
        }
  ]
}
```

### Insert Category

#### Request :

- Method : `POST`
- Endpoint : `/course/category`
- Body :

```json
{
    "name" : "",
    "code" : "",
    "createdBy" : ""
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

### Update Category

#### Request :

- Method : `PUT`
- Endpoint : `/course/category`
- Body :

```json
{
    "id" : "",
    "code" : "",
    "name" : "",
    "createdBy": "",
    "updateBy" : ""
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

### Update isActive Category

#### Request :

- Method : `PATCH`
- Endpoint : `/course/category`
- Body :

```json
{
    "id" : "",
    "updateBy" : "",
    "status" : ""
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

### Delete Course Cateogry

#### Request :

- Method : `DELETE`
- Endpoint : `/course/category/id/{id}`

#### Response :

```json
{
    "code": "",
    "result": ""
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```
-----------------------------------------------------------

## Course

### Course Available

#### Request :

- Method : `GET`
- Endpoint : `/course/available`
- Query Param : `studentId`

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

### Get Detail Course

#### Request :

- Method : `GET`
- Endpoint : `/course/{courseId}`
- Query Param : `studentId`

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
                "title": "",
                "description": "",
                "subjectName": "",
                "attendanceId": "",
                "verifyStatus": "",
                "schedule": {
                    "id": "",
                    "date": "",
                    "startTime": "",
                    "endTime": ""
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

### Register Course

#### Request :

- Method : `POST`
- Endpoint : `course/register`
- Query Param : `studentId/courseId`

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

### Get List Course For Admin

#### Request :

- Method : `GET`
- Endpoint : `course/admin`

#### Response :

success

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
            "description": "",
            "typeId": "",
            "categoryId": "",
            "teacherId": "",
            "active": "",
            "firstName": "",
            "lastName": ""
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

### Get List Student By Course Id

#### Request :

- Method : `Get`
- Endpoint : `course/{id}/students`

#### Response :

success

```json
{
    "code": 200,
    "result": [
        {
            "studentCourseId": "",
            "studentId": "",
            "code": "",
            "firstName": "",
            "lastName": "",
            "email": "",
            "phone": "",
            "gender": "",
            "isVerified": ""
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

### Insert Course

#### Request:

- Method: `POST`
- Endpoint: `course`
- Body :

```json
{
    "code" : "",
    "description" : "",
    "courseTypeId" : "",
    "teacherId" : "",
    "courseCategoryId" : "",
    "capacity" : "",
    "periodStart" : "",
    "periodEnd" : "",
    "createdBy" : ""
}
```

#### Response

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

### Update Course

#### Request:

- Method: `Put`
- ENDPOINT: `course`
- Body :

```json
{
    "id" : "",
    "code" : "",
    "description" : "",
    "courseTypeId" : "",
    "teacherId" : "",
    "courseCategoryId" : "",
    "capacity" : "",
    "periodStart" : "",
    "periodEnd" : "",
    "updateBy" : "",
    "status" : ""
}
```

#### Response

success

```json
{
  "code": 200,
  "result": "message"
}
```


### Delete Course

#### Request:

- Method: `DELETE`
- ENDPOINT: `course`
- Body :

```json
{
    "id" : "",
    "updateBy" : ""
}
```

#### Response

success

```json
{
  "code": 200,
  "result": "message"
}
```

### Get Course Attendance Report

#### Request

- Method: `GET`
- Endpoint: `course/attendance/reports/{courseId}`

#### Response :

success

```json
{
    "code": 200,
    "result": [
        {
            "moduleId": "",
            "moduleName": "",
            "date": "",
            "absent": "",
            "present": "",
            "totalStudent": ""
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

### Get Course Progress By Id Student

#### Request

- Method: `GET`
- Endpoint: `course/progress/{studentId}`

#### Response :

success

```json
{
    "code": 200,
    "result": [
        {
            "courseId": "",
            "courseName": "",
            "periodEnd": "",
            "periodStart": "",
            "totalModule": "",
            "moduleComplete": "",
            "percentProgress": ""
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

### Update Course Status

#### Request

- Method: `PATCH`
- Endpoint: `course/status`
- Body : 
  
```json
{
    "id" : "",
    "status" : "",
    "updateBy" : ""
}
```

#### Response

success

```json
{
  "code": 200,
  "result": "message"
}
```

----------------------------------------------------
## Course Type Controller 

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
      "name": "",
      "version": "",
      "active" : ""
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

### Update Course Type

#### Request

- Method : `PUT`
- Endpoint : `/course/type`
- Body:

```json
{
  "id": "",
  "code": "",
  "name": "",
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

### Update Is Active Course Type

#### Request

- Method : `Patch`
- Endpoint : `/course/type`
- Body:

```json
{
  "id": "",
  "updatedBy": "",
  "status" : ""
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
- Endpoint : `/course/type/id/{id}`

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

-------------------------------------------------
## Exam Controller 

### Get Average Score 

#### Request

- Method : `GET`
- Endpoint : `exam/average-scores/student/{id}`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "averageScore": "",
      "code": "",
      "title": "",
      "startTime": "",
      "endTime" : ""
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

### Get Detail Module Exam 

#### Request

- Method : `GET`
- Endpoint : `exam/module/{id}`

#### Response :

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
      "type" : "",
      "startTime": "",
      "endTime": "",
      "fileId": "",
      "version": "",
      "fileName" : ""
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

### Get Exam Submission

#### Request

- Method : `GET`
- Endpoint : `exam/{id}/submission`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "fileId": "",
      "fileName": "",
      "code": "",
      "firstName" : "",
      "lastName": "",
      "grade": "",
      "submittedDate": ""
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

### Get Student Exam Submission

#### Request

- Method : `GET`
- Endpoint : `exam/{examId}/submission/{studentId}`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "detailId": "",
      "fileId": "",
      "fileName": "",
      "code": "",
      "firstName" : "",
      "lastName": "",
      "grade": "",
      "submittedDate": ""
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

### Send Student Exam

#### Request

- Method : `POST`
- Endpoint : `/exam/student`
- Request Part : `multipart file`
- Query Param : `examId/studentId`

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
### Send Teacher Exam

#### Request

- Method : `POST`
- Endpoint : `/exam/module`
- Request Part : `multipart file/body`
- Body:

```json
{
  "moduleId": "",
  "title": "",
  "description": "",
  "startTime": "",
  "endTime": "",
  "createdBy": "",
  "type": "",
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
### Update Score Exam

#### Request

- Method : `PUT`
- Endpoint : `/exam/submission`
- Body:

```json
{
  "id": "",
  "grade": "",
  "updateBy": "",
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

### Delete Exam

#### Request

- Method : `DELETE`
- Endpoint : `/exam/{id}`

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

### Delete Exam Sumission

#### Request

- Method : `DELETE`
- Endpoint : `/exam/submission/{id}`

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

--------------------------------------
## Experience Controller

### Create Experience

#### Request

- Method : `POST`
- Endpoint : `experience`
- Body:

```json
{
  "title": "",
  "description": "",
  "startDate": "",
  "endDate": "",
  "teacherId": "",
  "userId": "",
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

### Update Experience

#### Request

- Method : `PUT`
- Endpoint : `experience`
- Body:

```json
{
  "id": "",
  "title": "",
  "description": "",
  "startDate": "",
  "endDate": "",
  "teacherId": "",
  "userId": "",
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
- Endpoint : `experience`

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

------------------------------------------

## File Controller

### Donwload File By Id

#### Request

- Method : `GET`
- Endpoint : `file/{id}`

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

------------------------------------------

## Forum Controller

### Get Module Discussion

#### Request

- Method : `GET`
- Endpoint : `forum/module/{id}`

#### Response :

success

```json
{
  "code": 200,
  "result": {
            "id": "",
            "code": "",
            "content": "",
            "createdAt": "",
            "userId": "",
            "firstName": "",
            "lastName": "",
            "roleId": "",
            "roleCode": "",
            "photoId": "",
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

### Save Forum

#### Request

- Method : `POST`
- Endpoint : `forum`
- Body:

```json
{
  "userId": "",
  "moduleId": "",
  "content": ""
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

### Delete Discussion

#### Request

- Method : `DELETE`
- Endpoint : `forum`
- Query Param : `forumId/userId`

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

---------------------------------------------
## Guest Controller

### Get All Course

#### Request

- Method : `GET`
- Endpoint : `public/courses`

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
            "teacher" : {
                  "id": "",
                  "code": "",
                  "firstName": "",
                  "lastName": "",
                  "title": "",
                  "experience": "",
                  "photoId": ""
            },
            "totalModule": "",
            "moduleComplete": "",
            "percentProgress": ""
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

### Get Module List By Id Course

#### Request

- Method : `GET`
- Endpoint : `public/course/{id}`

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
            "totalStudent": "",
            "description": "",
            "periodStart": "",
            "periodEnd": "",
            "modules" : [
              {
                  "id": "",
                  "code": "",
                  "title": "",
                  "description": "",
                  "subjectName": "",
                  "isActive": ""
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

### Get All Teacher

#### Request

- Method : `GET`
- Endpoint : `public/teachers`

#### Response :

success

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
            "active": "",
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

------------------------------------

## Module Controller

### Get Detail Module

#### Request

- Method : `GET`
- Endpoint : `module/{id}`

#### Response :

success

```json
{
    "code": 200,
    "result": {
        "id": "",
        "createdBy": "",
        "createdAt": "",
        "updatedBy": "",
        "updatedAt": "",
        "version": "",
        "isActive": "",
        "code": "",
        "title": "",
        "description": "",
        "schedule": {
            "id": "",
            "createdBy": "",
            "createdAt": "",
            "updatedBy": "",
            "updatedAt": "",
            "version": "",
            "isActive": "",
            "code": "",
            "date": "",
            "startTime": "",
            "endTime": "",
            "teacher": ""
        },
        "course": "",
        "subject": ""
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

### Insert Module

#### Request

- Method : `POST`
- Endpoint : `module`
- Body:

```json
[
    {
        "code": "",
        "title": "",
        "description": "",
        "courseId": "",
        "subjectId": "",
        "schedule": {
            "date": "",
            "startTime": "",
            "endTime": "",
            "createdBy": ""
        },
        "createdBy": "",
    }
]
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

### Delete Module

#### Request

- Method : `DELETE`
- Endpoint : `module`
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

### Update Is Active

#### Request

- Method : `PATCH`
- Endpoint : `module/true`
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

### Update Module

#### Request

- Method : `PUT`
- Endpoint : `module`
- Body:

```json
{
        "id": "",
        "updatedBy": "",
        "code": "",
        "title": "",
        "description": "",
        "courseId": "",
        "subjectId": "",
        "schedule": {
            "id": "",
            "date": "",
            "startTime": "",
            "endTime": ""
        }
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

### Insert Lesson

#### Request

- Method : `POST`
- Endpoint : `module/lesson`
- Request Part : `file`
- Query Param : `idUser/idModule`

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

### Delete Lesson

#### Request

- Method : `DELETE`
- Endpoint : `module/lesson/{fileId}`

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

### Get Lesson

#### Request

- Method : `DELETE`
- Endpoint : `module/lesson/{idModule}`

#### Response :

success

```json
{
    "code": 200,
    "result": [
        {
            "id": "",
            "fileName": "",
            "fileType": "",
            "size": "",
            "contentType": "",
            "version": ""
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
--------------------------------
## Role Controleer

### Get All Role

#### Request

- Method : `GET`
- Endpoint : `roles`

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
            "version": "",
            "createdAt": ""
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
- Endpoint : `role/id/{id}`

#### Response :

success

```json
{
    "code": 200,
    "result": {
        "id": "",
        "code": "",
        "name": "",
        "version": "",
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

### Insert Role

#### Request

- Method : `POST`
- Endpoint : `role`
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

### Update Role

#### Request

- Method : `PUT`
- Endpoint : `role`
- Body:

```json
{
    "id": "",
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

--------------------------------
## Schedule Controller

### Get Schedule By Module

#### Request

- Method : `GET`
- Endpoint : `/schedules/teacher/{id}`

#### Response :

success

```json
{
    "code": 200,
    "result": [
        {
            "id": "",
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

-------------------------------------
## Student Controller

### Get Student Dashboard

#### Request

- Method : `GET`
- Endpoint : `/student/dashboard/{id}`

#### Response :

success

```json
{
    "code": 200,
    "result": {
        "id": "",
        "code": "",
        "username": "",
        "firstName": "",
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

### Get Student Course

#### Request

- Method : `GET`
- Endpoint : `/student/{id}/course`

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
                "title": "",
                "experience": "",
                "photoId": ""
            },
            "totalModule": "",
            "moduleComplete": "",
            "percentProgress": ""
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

### Update Student Profile

#### Request

- Method : `PUT`
- Endpoint : `student`
- Body:

```json
{
    "id": "",
    "username": "",
    "firstName": "",
    "lastName": "",
    "phone": "",
    "gender": "",
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

### Delete Student

#### Request

- Method : `DELETE`
- Endpoint : `student`
- Query Param : `studentId/ updatedBy`

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

### Register Student

#### Request

- Method : `POST`
- Endpoint : `student/register`
- Body:

```json
{
    "gender": "",
    "phone": "",
    "createdBy": "",
    "email": "",
    "firstName": "",
    "lastName": "",
    "password": "",
    "username": ""
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

### Get Student Report

#### Request

- Method : `GET`
- Endpoint : `/student/report`
- Query Param : `studentId`

#### Response :

success

```json
{
    "code": 200,
    "result": [
        {
            "courseName": "",
            "moduleName": "",
            "examType": "",
            "examTitle": "",
            "dateExam": "",
            "grade": ""
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

### Get All Student

#### Request

- Method : `GET`
- Endpoint : `/student/all`

#### Response :

success

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
            "email": "",
            "phone": "",
            "gender": "",
            "idPhoto": "",
            "createdAt": "",
            "isActive": ""
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

-----------------------------

## Subject Category Controller

### Get All Subject Cateogory

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
            "name": "",
            "description": "",
            "active": ""
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
### Get Subject Category By Id

#### Request

- Method : `GET`
- Endpoint : `/subjectcategory/id{id}`

#### Response :

success

```json
{
    "code": 200,
    "result": {
        "id": "",
        "code": "",
        "name": "",
        "description": "",
        "active": ""
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

### Insert Subject Cateogry

#### Request

- Method : `POST`
- Endpoint : `subjectcategory`
- Body:

```json
{
    "code": "",
    "description": "",
    "subjectName": "",
    "createdBy": ""
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

### Update Subject Cateogry

#### Request

- Method : `PUT`
- Endpoint : `subjectcategory`
- Body:

```json
{
    "id": "",
    "code": "",
    "description": "",
    "subjectName": "",
    "createdBy": ""
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

### Update Is Active Subject Cateogry

#### Request

- Method : `PATCH`
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

### Delete Subject Cateogry

#### Request

- Method : `DELETE`
- Endpoint : `subjectcategory/{id}`

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

-----------------------------------
## Teacher Controller

### Get All Teacher

#### Request

- Method : `GET`
- Endpoint : `/teacher`

#### Response :

success

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
            "active": ""
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

### Get Teacher Profile

#### Request

- Method : `GET`
- Endpoint : `/teacher/{id}`

#### Response :

success

```json
{
    "code": 200,
    "result": {
        "id": "",
        "username": "",
        "firstName": "",
        "lastName": "",
        "email": "",
        "createdAt": "",
        "gender": "",
        "phone": "",
        "titleDegree": "",
        "photoId": "",
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

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Create Teacher Profile

#### Request

- Method : `POST`
- Endpoint : `teacher`
- Body:

```json
{
    "code" : "",
    "firstName" : "",
    "lastName" : "",
    "phone" : "",
    "gender" : "",
    "username" : "",
    "password" : "",
    "email" : "",
    "roleId" : "",
    "roleVersion" : "",
    "createdBy" : "",
    "titleDegree" : ""
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

### Update Teacher Profile

#### Request

- Method : `PUT`
- Endpoint : `teacher`
- Body:

```json
{
    "id" : "",
    "firstName" : "",
    "lastName" : "",
    "phone" : "",
    "gender" : "",
    "username" : "",
    "updatedBy" : "",
    "titleDegree" : ""
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

### Update Is Active Teacher Profile

#### Request

- Method : `PATCH`
- Endpoint : `teacher`
- Body:

```json
{
    "id" : "",
    "updatedBy" : "",
    "status" : ""
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

### Delete Teacher By Id

#### Request

- Method : `DELETE`
- Endpoint : `teacher/id/{id}`

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

### Get Teacher Profile

#### Request

- Method : `GET`
- Endpoint : `/teacher/dashboard/{id}`

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
            "description": "",
            "capacity": "",
            "totalStudent": "",
            "totalModule": "",
            "periodStart": "",
            "periodEnd": ""
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

### Get Teacher Report

#### Request

- Method : `GET`
- Endpoint : `/teacher/reports/{id}`

#### Response :

success

```json
{
    "code": 200,
    "result": [
        {
            "studentId": "",
            "studentFirstName": "",
            "studentLastName": "",
            "totalExam": "",
            "totalAssignment": "",
            "notAssignment": "",
            "avgScore": ""
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

### Verify Student Register Course 

#### Request

- Method : `PUT`
- Endpoint : `/teacher/verify/student`
- Query Param : `studentCourseId/teacherId/email`

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

----------------------------------------
## User Controller

### Update Password User

#### Request

- Method : `PUT`
- Endpoint : `/user/update-password`
- Body:

```json
{
    "id" : "",
    "oldPassword" : "",
    "newPassword" : ""
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

### Update Password User

#### Request

- Method : `PATCH`
- Endpoint : `/user/forget-password`
- Query Param : `email`

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

### Save User Photo

#### Request

- Method : `PUT`
- Endpoint : `/user/photo`
- Request Part : `file , content`

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