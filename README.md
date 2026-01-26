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



