# CRM_App - QUICKRES

## Table of Contents
|   |   |
|---|---|
| [1. Overview](#overview) | [6. Firms](#firms) |
| [2. Technologies](#technologies) | [7. Leads](#leads) |
| [3. Features](#features) | [8. Expenses](#expenses) |
| [4. Navigation](#navigation) | [9. Dependencies](#dependencies) |
| [5. Employees](#employees) |



<div style="display: flex; flex-wrap: wrap;">
    <img src="https://github.com/akinemreyazici/CRM_App/assets/116732291/1151c3c3-e2ef-4a01-96de-f0a98c003587" alt="CRM App Logo">
</div>

## Overview

CRM_App is a mobile Customer Relationship Management (CRM) application. This application enables company owners and managers to track their employees. Additionally, it allows viewing, adding, updating, and deleting all customer and supplier information. Furthermore, managers can analyze employee expenses graphically and manage categorized feedback received from customers.

## Technologies

| Technology | Description |
|------------|-------------|
| **Retrofit** | Used for handling RESTful API requests to fetch data externally. |
| **MPAndroidChart** | Utilized for graphical data visualization. |
| **RecyclerView** | Employed for listing and visualization purposes. |

## Features

1. **User Management:** Managers and company owners can add, delete, and update employees. Additionally, they can manage the access levels of users.

2. **Customer and Supplier Management:** All customer and supplier information can be viewed, added, updated, and deleted. Moreover, this information can be filtered by categories.

3. **Expense Analysis:** Employee expenses can be visualized through graphs. These graphs may include totals of expenses within a specific time frame, expense categories, and other relevant data.

4. **Lead Management:** This section serves as a space where employees can easily list and view the leads they receive, organized by the nature of the lead, such as positive, negative, or suggestions for improvement. Additionally, they can manage the status of these leads effortlessly.


## Navigation


<div style="display: flex; flex-wrap: wrap;">
    <img width="300" alt="crm1" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/a48a9d49-9415-4772-bbcd-6545e4e359c8">
    <img width="300" alt="proje_navigation" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/2177da7e-cb95-41f7-9111-9be73f53e167">
</div>

## Employees

<div style="display: flex; flex-wrap: wrap;">
    <img width="300" alt="proje_employeList" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/9ec60eb6-9372-4fb4-b6d3-190d028c7b54">
    <img width="300" alt="proje_employeeAdd" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/19e7cda2-965c-481e-9839-24a01ccf1ce8">
    <img width="300" alt="employee_Detail" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/97c2096f-da58-4d8c-81c6-b11ae8f7b75b">
</div>

## Firms

<div style="display: flex; flex-wrap: wrap;">
    <img width="300" alt="proje_firmaList" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/a24de947-08e1-49f5-83e1-e1366e670e25">
    <img width="300" alt="proje_firmaAdd" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/d1841370-9331-4511-a67c-40d874c2db14">
    <img width="300" alt="proje_firmDetail" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/486e342a-9b16-4aae-a689-a4dcb426af44">
</div>

## Leads

<div style="display: flex; flex-wrap: wrap;">
    <img width="300" alt="proje_leadList" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/64e39152-9119-4e6c-98af-f64e46bd1e3c">
    <img width="300" alt="proje_leadAdd" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/74ae8af6-e253-4e63-90be-de9344df035b">
    <img width="300" alt="proje_leadDetail" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/1dbe4b52-32d5-449b-bea9-02ebe2c1f7a0">
</div>

## Expenses

<div style="display: flex; flex-wrap: wrap;">
    <img width="300" alt="proje_expenseAdd" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/caf24cef-2049-4cae-97b3-b98b0df4ed13">
    <img width="300" alt="proje_expenseList" src="https://github.com/akinemreyazici/CRM_App/assets/116732291/7938cc4a-e7f0-4bb0-90bc-c6ada15efd2a">
</div>

## Dependencies

```kotlin
dependencies {
    implementation 'com.github.bumptech.glide:glide:4.15.1'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.10.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.2'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.7.4'
    implementation 'androidx.navigation:navigation-ui-ktx:2.7.4'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    implementation 'org.jsoup:jsoup:1.16.1'
    implementation 'com.github.bumptech.glide:glide:4.15.1'
    implementation 'com.github.Philjay:MPAndroidChart:v3.1.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
}
```
