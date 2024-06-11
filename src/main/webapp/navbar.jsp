<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Library Management</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="manageBooks.jsp">Manage Books</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="manageMembers.jsp">Manage Members</a>
            </li>
        </ul>
        <ul class="navbar-nav">
            <c:choose>
                <c:when test="${not empty sessionScope.librarian}">
                    <li class="nav-item">
                        <a class="nav-link" href="#">Welcome, ${sessionScope.librarian.name}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="logoutServlet">Logout</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Login</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>