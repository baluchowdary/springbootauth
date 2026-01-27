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


