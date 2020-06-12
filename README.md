# InventoryManagementSystem

It is a platform where retailers can register their companies and manage their inventory very easily. Employees and admins can register themselves and access the portal.
Security is ensured by giving different access to admin and employees. The admin will be previllaged to add,delete or update product details and also track the investment
and sales report whereas employees are restricted to do so.

The database structure is as follows:

inventorymgtsys is a scheme with the tables:
->employees
->retailers
->products
->sales

The user and company passwords are encrypted using bcrypt hashing function.

user login page:
![login img](userLogin.png)

user registration page:
![user registration page](userReg.png)

company registration page:
![comp reg img](companyreg.png)

employee portal page:
![emp pg](employeepage.png)

admin portal page:
![admin pg](adminpage)
