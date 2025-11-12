# Job Hunter - Frontend

## Overview

The frontend of the Job Hunter project is a React application built with Vite. The application consists of two main sections: the **Admin Dashboard** and the **Client Interface**. The Admin Dashboard is designed for managing job listings, applications, and users, while the Client Interface allows job seekers and employers to interact with the platform.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Demo](#demo)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Configuration](#configuration)
- [Scripts](#scripts)
- [Dependencies](#dependencies)
- [Usage](#usage)
- [Contributing](#contributing)

## Features

- **Admin Dashboard**: 
  - Manage job listings, applications, and users.
  - Dashboard with key metrics and statistics.
  - Role-based access control for secure management.

- **Client Interface**:
  - Job seekers can browse, filter, and apply for jobs.
  - Employers can post and manage job listings.
  - User authentication and profile management.

## Tech Stack

- **Frontend Framework**: React 18
- **Build Tool**: Vite
- **UI Library**: Ant Design (antd)
- **State Management**: Redux Toolkit
- **Routing**: React Router DOM
- **HTTP Client**: Axios
- **Other Libraries**:
  - `dayjs` for date manipulation.
  - `lodash` for utility functions.
  - `react-quill` for rich text editing.
  - `react-icons` for icon usage.
  - `spring-filter-query-builder` for filtering and query building.

## Demo

### Admin Dashboard

|   Companies  | Create user  |     Users    |
|--------------|--------------|--------------|
| ![image](https://github.com/user-attachments/assets/7939da84-87ea-4f2d-aec3-cfe5079cd9f2) | ![image](https://github.com/user-attachments/assets/5f4b7acb-dbdf-4013-aa14-41ab6d75a50c) | ![image](https://github.com/user-attachments/assets/f1a96882-2dd3-4cbd-8471-ed32af09696b) |

| Permissions  |     Roles    |  Create role |
|--------------|--------------|--------------|
| ![image](https://github.com/user-attachments/assets/d6eeb5c3-26d9-4e90-8bb9-a00219a32c57) | ![image](https://github.com/user-attachments/assets/bf79edac-e65d-434b-a63a-8bbf1d892f60) | ![image](https://github.com/user-attachments/assets/607f63ea-c64b-41e0-8284-5276a1ecb1e9) |

### HR Dashboard

|     Job      |    Resume    |
|--------------|--------------|
| ![image](https://github.com/user-attachments/assets/2d390e2d-4cf4-40c1-8dfe-eee3884f0321) | ![image](https://github.com/user-attachments/assets/ff476a86-85c1-4663-84b3-b06cb93a2d17) |

### Client page

|   Homepage   |  Filter job  |  Upload CV   | Check status |
|--------------|--------------|--------------|--------------|
| ![image](https://github.com/user-attachments/assets/2c00d14f-c5e8-4a85-9041-0780fa38d210) | ![image](https://github.com/user-attachments/assets/bca9953f-09d5-471d-9d5f-f3085ca7f529) | ![image](https://github.com/user-attachments/assets/2c6caf12-cd3b-4029-9f73-3ab3e9a6627c) | ![image](https://github.com/user-attachments/assets/85423b5a-7fe3-4bc0-9547-f4d5a73eb558) |

## Project Structure

    public
    src/
    ├── assets/             # Static assets like images, icons, etc.
    ├── components/         # Reusable components across the application
    ├── config/             # General configuration for the project
    ├── pages/              # Application pages (Admin and Client)
    │   ├── admin/          # Admin dashboard pages
    │   │── auth/           # Login & register pages
    │   │── company/        # Company details page
    │   │── home/           # Home page
    │   └── job/            # Job details page
    ├── redux/              # Redux store configuration
    ├── types/              # Definition of backend objects
    ├── styles/             # Global and component-specific styles
    ├── App.jsx             # Main application component
    └── main.jsx            # Entry point of the application
    index.html
    .env.development
    .env.production
    .env.uat

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/TranDatk/Job-Hunter.git
    cd 03-react-vite-jobhunter-final
2. Install dependencies:
    ```bash
    npm install

## Configuration

1. Environment Variables:
* Create a .env.development file in the root directory and add the following variables:
    ```plaintext
    NODE_ENV=development
    PORT=3000
    VITE_BACKEND_URL=http://localhost:8080
    VITE_ACL_ENABLE=true
* In production:
    ```plaintext
    PORT=3000
    VITE_BACKEND_URL=http://localhost:8080
    VITE_ACL_ENABLE=true
* Create a .env.uat file for run by docker:
    ```plaintext
    PORT=3000
    VITE_BACKEND_URL=
    VITE_ACL_ENABLE=true

## Scripts

* Start the development server:
    ```bash
    npm run dev

* Build for production:
    ```bash
    npm run build

* Preview the production build:
    ```bash
    npm run preview

* Build for run docker compose file:
    ```bash
    npm run build-uat

## Dependencies

Here are the key dependencies used in this project:

UI Libraries:
  * `antd`: Provides a set of high-quality React components out of the box.
  * `@ant-design/pro-components`, `@ant-design/pro-layout`: Enhances the admin dashboard with professional components and layouts.
  * `react-icons`: Easy-to-use icons in React.
State Management:
  * `@reduxjs/toolkit`: Official, opinionated toolset for efficient Redux development.
  * `react-redux`: Official React bindings for Redux.
Routing:
  * `react-router-dom`: Declarative routing for React applications.
HTTP Client:
  * `axios`: Promise-based HTTP client for the browser and Node.js.
Utilities:
  * `lodash`: A modern JavaScript utility library delivering modularity, performance, and extras.
  * `dayjs`: A lightweight JavaScript date library for parsing, validating, manipulating, and formatting dates.
Text Parsing and Editing:
  * `html-react-parser`: Converts HTML strings into React components.
  * `react-quill`: A Quill component for React for rich text editing.
Miscellaneous:
  * `react-countup`: A React component wrapper around CountUp.js.
  * `react-device-detect`: Detect device type (mobile, tablet, desktop) for rendering components conditionally.
  * `spring-filter-query-builder`: Helps build filter queries for Spring Boot applications.

## Usage

### Running the Application
1. Development:
  * Start the development server using npm run dev.
  * Access the application in your browser at http://localhost:3000.
2. Production:
  * Build the production-ready application using npm run build.
  * Preview the build with npm run preview.
### Admin Dashboard
  * Access the Admin Dashboard to manage job listings, users, and applications.
  * Admins have role-based access to specific functionalities within the dashboard.
  * Employers can post and manage their job listings.
### Client Interface
  * Job seekers can browse and apply for jobs.

## Contributing

Contributions are welcome! Please follow the steps below to contribute:

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Make your changes and commit them (git commit -m 'Add new feature').
4. Push to the branch (git push origin feature-branch).
5. Open a pull request.
