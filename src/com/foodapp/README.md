# Online Food Ordering System - Final Complete

Menu-driven Java console application with persistence and reports.
Sample accounts:
- Customer: cust1 / pass1
- Admin: admin / admin123
- Delivery: del1 / del123

Run (Windows PowerShell):
$files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.foodapp.app.Main
