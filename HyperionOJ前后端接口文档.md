# HyperionOJ接口文档

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

###    

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

