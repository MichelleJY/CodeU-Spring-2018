<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<html>
<head>
   <%@include file="navigationbar.jsp" %>
  <title>Logout</title>
  <link rel="stylesheet" href="/css/main.css">
    <style>
    label {
      display: inline-block;
      width: 100px;
    }
  </style>
</head>
<body>
  <div id="container">
    <h1>Logout</h1>
    <form action="/logout" method="POST">
        <br/>
      <button type="submit">Logout</button>
    </form>
  </div>
</body>
</html>
