# HyperionOJ接口文档

[TOC]



## oss单点登录

### 普通用户登录

接口url：localhost:9000/login/user

请求方式： post

请求参数：

```
{
    "account": 15570357290,
    "password": 123212321
}
```

返回数据：

```json
{
  "code": 200,
  "data": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mzg3MDcxMzcsInVzZXJJZCI6MTU1NzAzNTcyOTAsImlhdCI6MTYzODYyMDczN30.s0LB0IKWlZAGUk1D3dxmdqzkO4Xt4N2T4R5eAIhReho",
  "msg": null
}
```



### 普通用户注册

接口url：localhost:9000/register/user

请求方式： post

请求参数：

```json
{
  "id": 15570357290,
  "username": "冰箱的主人",
  "password": 123212321,
  "mail": "Hyperion_LR@foxmail.com"
}
```

返回数据：

```json
{
  "code": 200,
  "data": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mzg3MDcxMzcsInVzZXJJZCI6MTU1NzAzNTcyOTAsImlhdCI6MTYzODYyMDczN30.s0LB0IKWlZAGUk1D3dxmdqzkO4Xt4N2T4R5eAIhReho",
  "msg": null
}
```



### 发送验证码

接口url：localhost:9000/vercode

请求方式：post

请求参数

```json
{
  "mail": "Hyperion_LR@foxmail.com",
  "subject": "注册账号"
}
```

返回数据：

```json
{
  "code": 200,
  "data": null,
  "msg": null
}
```



### 更新用户数据

接口url：localhost:9000/update/user

请求方式：post

请求参数：

```json
{
  "id": 15570357290,
  "username": "冰箱的主人",
  "avatar": "...",
  "mail": "Hyperion_LR@foxmail.com"
}
```

返回参数：

```json
{
  "code": 200,
  "data": null,
  "msg": null
}
```



### 更新用户密码

接口url：localhost:9000/update/password

请求方式：post

请求参数：

```json
{
  "userMail": "Hyperion_LR@foxmail.com",
  "code": 1234,
  "password": "123212321"
}
```

返回参数：

```json
{
  "code": 200,
  "data": null,
  "msg": null
}
```



### 销毁账号

接口url：localhost:9000/destroy

请求方式：post

请求参数：

```json
{
  "account": 15570357290,
  "password": 123212321
}
```

返回参数

```json
{
  "code": 200,
  "data": null,
  "msg": null
}
```



## page

### 获取题目归档

接口url：localhost:9000/problem/list

请求方式：get

请求参数：

```json
{
    "page": 1,
    "pageSize": 10,
    "level": null,
    "categoryId": null
}
```

返回参数

```json
{
    "code": 200,
    "data": [
        {
            "id": "1",
            "title": "test",
            "bodyId": "1",
            "problemLevel": 1,
            "categoryId": "1",
            "acNumber": 0,
            "submitNumber": 0,
            "solutionNumber": 0,
            "commentNumber": 0
        },
        {
            "id": "2",
            "title": "test2",
            "bodyId": "2",
            "problemLevel": 1,
            "categoryId": "1",
            "acNumber": 0,
            "submitNumber": 0,
            "solutionNumber": 0,
            "commentNumber": 0
        }
    ],
    "msg": null
}
```





### 获取题目列表

接口url：localhost:9000/problem/list

请求方式：get

请求参数：

```json
{
    "page": 1, 			// 页号
    "pageSize": 10,		// 页大小
    "level": null, 		// 题目难度
    "categoryId": null	// 分类id
}
```

返回参数

```json
{
    "code": 200,
    "data": [
        {
            "id": "1",
            "title": "test",
            "bodyId": "1",
            "problemLevel": 1,
            "categoryId": "1",
            "acNumber": 0,
            "submitNumber": 0,
            "solutionNumber": 0,
            "commentNumber": 0,
            "problemBodyVo": null
        },
        {
            "id": "2",
            "title": "test2",
            "bodyId": "2",
            "problemLevel": 1,
            "categoryId": "1",
            "acNumber": 0,
            "submitNumber": 0,
            "solutionNumber": 0,
            "commentNumber": 0,
            "problemBodyVo": null
        }
    ],
    "msg": null
}
```



### 通过id查看题目

接口url：localhost:9000/problem/{id}

请求方式：get

请求参数：

```json
{
}
```

返回参数

```json
{
    "code": 200,
    "data": {
        "id": "1",
        "title": "test",
        "bodyId": "1",
        "problemLevel": 1,
        "categoryId": "1",
        "acNumber": 0,
        "submitNumber": 0,
        "solutionNumber": 0,
        "commentNumber": 0
    },
    "msg": null
}
```



### 提交题目

接口url：localhost:9000/problem/{id}

请求方式：get

请求参数：

```json
{
    "authorId": 15570357290,
    "problemId": 1,
    "codeLang": "java",
    "codeBody": "import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}",
    "runTime": 1000,
    "runMemory": 256,
    "caseNumber": 1
}
```

返回参数

```json
{
    "code": 200,
    "data": {
        "authorId": "15570357290",
        "submitId": null,
        "problemId": 1,
        "verdict": "AC",
        "msg": "Hyperion\n3\n7\n5\nHyperion\n",
        "runTime": 126,
        "runMemory": 0
    },
    "msg": null
}
```





## 管理员

### 管理员登录

接口url：localhost:9000/login/admin

请求方式： post

请求参数：

```
{
    "account": 1,
    "password": 123456
}
```

返回数据：

```json
{
    "code": 200,
    "data": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzkzMDgzNDksInVzZXJJZCI6MSwiaWF0IjoxNjM5MjIxOTQ5fQ.EmQ6gJXgavQORt4jy5LqsZN6oAFqYpFgyJ1nibrYTn0",
    "msg": null
}
```



###   添加管理员

接口url：localhost:9000/admin/add

请求方式： post

请求参数：

```
{
    "id": 1234,
    "name": "Hyperion",
    "password": 123456,
    "permissionLevel": 0
}
```

返回数据：

```json
{
    "code": 200,
    "data": {
        "id": 1234,
        "name": "Hyperion",
        "password": "083f3977344768987d0e1d315b358f90",
        "permissionLevel": 0,
        "salt": "HyperionOJ"
    },
    "msg": null
}
```



###   更新管理员信息

接口url：localhost:9000/admin/update

请求方式： post

请求参数：

```
{
    "id": 1234,
    "name": "Hyperion",
    "password": 123456,
    "permissionLevel": 1
}
```

返回数据：

```json
{
    "code": 200,
    "data": {
        "id": 1234,
        "name": "Hyperion",
        "password": "083f3977344768987d0e1d315b358f90",
        "permissionLevel": 1,
        "salt": "HyperionOJ"
    },
    "msg": null
}
```



###   删除管理员账号

接口url：localhost:9000/admin/delete

请求方式： post

请求参数：

```json
{
    "id": 1234,
    "name": "Hyperion"
}
```

返回数据：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



###   冻结普通用户账号

接口url：localhost:9000/admin/freeze

请求方式： post

请求参数：

```json
{
    "id": 15570357290
}
```

返回数据：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



###   查询管理员行为

接口url：localhost:9000/admin/query/action

请求方式： post

请求参数：

```json
{
    "page": 1,
    "pageSize": 3,
    "adminId": 1,
    "action": "/add"
}
```

返回数据：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1469629746873593858",
            "adminId": "1",
            "action": "/add",
            "time": "1639222010678",
            "status": 0
        },
        {
            "id": "1469495868724826114",
            "adminId": "1",
            "action": "/add",
            "time": "1639190091679",
            "status": 1
        },
        {
            "id": "1469495858423615489",
            "adminId": "1",
            "action": "/add",
            "time": "1639190089124",
            "status": 0
        }
    ],
    "msg": null
}
```

###    