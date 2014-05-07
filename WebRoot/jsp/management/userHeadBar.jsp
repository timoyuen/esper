<% String curPage = (String)request.getParameter("curPage"); %>
<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
  <div class="container">
    <div class="navbar-header">
      <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a href="home" class="navbar-brand"><strong>ESPER-TEMPLATE</strong></a>
    </div>
    <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
      <ul class="nav navbar-nav">
        <li <% if (curPage.equals("viewTemplate")) out.println("class='active'"); %> >
          <a href="management?method=viewTemplate">View</a>
        </li>
        <li <% if (curPage.equals("viewHistory")) out.println("class='active'"); %> >
          <a href="management?method=viewHistory">History</a>
        </li>
        <li <% if (curPage.equals("writePropose")) out.println("class='active'"); %> >
          <a href="management?method=writePropose">Propose</a>
        </li>
        <li <% if (curPage.equals("editProfile")) out.println("class='active'"); %> >
          <a href="management?method=editProfile">Profile</a>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li>
            <form action="management?method=searchTag" role='form' method='post' class='form-inline'>
              <div class="form-group">
                <input type="text" placeholder='keywords, tags' name="tag" class='form-control'/>
              </div>
              <button type="submit" class="btn btn-default">Search</button>
            </form>
        </li>
        <li>
          <a href="management?method=logout">Logout</a>
        </li>
      </ul>
    </nav>
  </div>
</header>
<br/>