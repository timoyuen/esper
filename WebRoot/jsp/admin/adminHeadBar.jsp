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
      <a href="home" class="navbar-brand"><strong>ADMIN</strong></a>
    </div>
    <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
      <ul class="nav navbar-nav">
        <li <% if (curPage.equals("newRule")) out.println("class='active'"); %> >
          <a href="admin?method=newRule">add temp</a>
        </li>
        <li <% if (curPage.equals("newStockCode")) out.println("class='active'"); %> >
          <a href="admin?method=newStockCode">add stock code</a>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li>
          <a href="admin?method=alogout">Logout</a>
        </li>
      </ul>
    </nav>
  </div>
</header>
<br/>