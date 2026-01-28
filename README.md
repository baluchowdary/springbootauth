==>get - http://localhost:9090/products/test
o/p: Testing endpoint is not secure

==>post - http://localhost:9090/products/saveproduct
I/p:
{
    "productName": "Credit Card"
}

o/p:
{
    "id": 1,
    "productName": "Credit Card"
}

==> get - http://localhost:9090/products/getproduct
o/p:
[
  {
    "id": 1,
    "productName": "Credit Card"
  }
]

==>put - http://localhost:9090/products/1
o/p:
{
    "id": 1,
    "productName": "test"
}

==============
==> post - http://localhost:9090/users/saveusermodel
i/p:
{
    "name": "bhardwaja",
    "password" : "bhardwaja",
    "roles" : "ROLE_LEAD"
}

OR

{
    "name": "kollu",
    "password" : "kollu",
    "roles" : "ROLE_ADMIN"
}

==> get - http://localhost:9090/users/getusermodel

ADMIN role use can able to fetch the details by passing user and password
LEAD roles can't able to fetch the details due to ROLE level access

==============jwt
==> post - http://localhost:9090/users/saveusermodel

--> Adding username & password
i/p: 
{
    "username": "kollu2",
    "password": "kollu2",
    "roles": "ROLE_ADMIN"
}

o/p:

{
    "id": 2,
    "username": "kollu2",
    "password": "$2a$10$Fo5HuSX0I72AeFSeh9vOBeA1TDSL4XQi.QvuU6kfrY.CCHmHX4cYS",
    "roles": "ROLE_ADMIN"
}

Authenticate
==> post - http://localhost:9090/users/authenticate

i/p:

{
    "username": "kollu",
    "password": "kollu"
}

o/p:

{
    "data": "eyJhbGciOiJIUzM4NCJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6ImtvbGx1IiwiaWF0IjoxNzY5NTcxNTExLCJleHAiOjE3NzczNDc1MTF9.hAPhuxDC2OdoHxoqv7Xio9FHfN4cQ112eUhcnzKdt11YBLnYTyYwWyEoeaxPYiGD",
    "status": true,
    "message": "Login Successful"
}

==>Authorize 

==> post - http://localhost:9090/users/getusermodel

i/p:

Authorization : Bearer Token

Token : eyJhbGciOiJIUzM4NCJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6ImtvbGx1IiwiaWF0IjoxNzY5NTY2NDI0LCJleHAiOjE3Njk1NjgyMjR9.P0QPl_ErFTYm5mhyRrPT-enZrRHePF8lDWEMCcVCCDNEEhTwoFRk8IcNSTotnOyE

o/p:

[
    {
        "id": 1,
        "username": "kollu",
        "password": "$2a$10$5OGZzdiQOE1nmTL1zR9VpukToQ5MuONNri5RDvRz5Y2/j2sqbKCKS",
        "roles": "ROLE_ADMIN"
    },
    {
        "id": 2,
        "username": "kollu2",
        "password": "$2a$10$Fo5HuSX0I72AeFSeh9vOBeA1TDSL4XQi.QvuU6kfrY.CCHmHX4cYS",
        "roles": "ROLE_ADMIN"
    }
]





