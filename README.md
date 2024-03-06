# CRM_App - Quick Release

## Table of Contents
|   |   |
|---|---|
| [1. Overview](#overview) | [6. Actions](#actions) |
| [2. Technologies](#technologies) | [7. Profile](#profile) |
| [3. Features](#features) | [8. Permissions](#permissions) |
| [4. Authentication](#authentication) | [9. Dependencies](#dependencies) |
| [5. Homepage](#homepage) | [10. Demo](#demo) |



<div style="text-align:center;">
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

4. **Feedback Management:** Received feedback can be listed, viewed, and managed within the application. For instance, filtering feedback by categories such as positive, negative, or suggestions for improvement is supported.

5. **User Profile:** Users can view and update their profile information, including changing passwords if needed.

## Authentication

<div style="display: flex; flex-wrap: wrap;">
    <img width="200" alt="crm1" src="https://example.com/crm_auth1.png">
    <img width="200" alt="crm2" src="https://example.com/crm_auth2.png">
    <img width="200" alt="crm3" src="https://example.com/crm_auth3.png">
    <img width="200" alt="crm4" src="https://example.com/crm_auth4.png">
</div>

## Homepage

<div style="display: flex; flex-wrap: wrap;">
    <img width="200" alt="crm5_1" src="https://example.com/crm_home1.png">
    <img width="200" alt="crm5_2" src="https://example.com/crm_home2.png">
    <img width="200" alt="crm5_3" src="https://example.com/crm_home3.png">
    <img width="200" alt="crm5_4" src="https://example.com/crm_home4.png">
</div>

## Actions

<div style="display: flex; flex-wrap: wrap;">

  <img width="200" alt="crm6_1" src="https://example.com/crm_actions1.png">
  <img width="200" alt="crm6_2" src="https://example.com/crm_actions2.png">
  <img width="200" alt="crm6_3" src="https://example.com/crm_actions3.png">
  <img width="200" alt="crm6_4" src="https://example.com/crm_actions4.png">
</div>

## Profile

<div style="display: flex; flex-wrap: wrap;">
      <img width="200" alt="crm7_1" src="https://example.com/crm_profile1.png">
      <img width="200" alt="crm7_2" src="https://example.com/crm_profile2.png">
      <img width="200" alt="crm7_3" src="https://example.com/crm_profile3.png">
      <img width="205" alt="crm7_4" src="https://example.com/crm_profile4.png">
</div>

## Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" /> 
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> 
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
