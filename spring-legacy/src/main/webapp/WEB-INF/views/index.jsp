<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%-- include head.jsp --%>
    <jsp:include page="/WEB-INF/views/include/head.jsp"/>
</head>
<body>
    <%-- include topNavbar.jsp --%>
    <jsp:include page="/WEB-INF/views/include/topNavbar.jsp"/>

    <!-- Carousel -->
    <div id="carouselExampleControls" class="carousel slide container" data-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="/resources/images/kirby1.jpg" class="d-block w-100">
        </div>
        <div class="carousel-item">
          <img src="/resources/images/kirby2.jpg" class="d-block w-100">
        </div>
        <div class="carousel-item">
          <img src="/resources/images/kirby3.jpg" class="d-block w-100">
        </div>
        <div class="carousel-item">
          <img src="/resources/images/kirby4.jpg" class="d-block w-100">
        </div>
      </div>
      <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
    <!-- end of Carousel -->

    <div class="container text-center mt-5">
      <div class="row">
        <div class="col-lg-4">
          <img src="/resources/images/kirby1.jpg" width="140" height="140" class="rounded-circle">
          <h2>Heading</h2>
          <p>Some representative placeholder content for the three columns of text below the carousel. This is the first column.</p>
          <p><a class="btn btn-secondary" href="#">View details &raquo;</a></p>
        </div><!-- /.col-lg-4 -->
        <div class="col-lg-4">
          <img src="/resources/images/kirby2.jpg" width="140" height="140" class="rounded-circle">
          <h2>Heading</h2>
          <p>Another exciting bit of representative placeholder content. This time, we've moved on to the second column.</p>
          <p><a class="btn btn-secondary" href="#">View details &raquo;</a></p>
        </div><!-- /.col-lg-4 -->
        <div class="col-lg-4">
          <img src="/resources/images/kirby3.jpg" width="140" height="140" class="rounded-circle">
          <h2>Heading</h2>
          <p>And lastly this, the third column of representative placeholder content.</p>
          <p><a class="btn btn-secondary" href="#">View details &raquo;</a></p>
        </div><!-- /.col-lg-4 -->
      </div><!-- /.row -->
    </div>

    <div class="container mt-5">
      <!-- Card Deck -->
      <div class="card-deck">
        <div class="card">
          <img src="/resources/images/kirby1.jpg" class="card-img-top">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
          </div>
        </div>
        <div class="card">
          <img src="/resources/images/kirby2.jpg" class="card-img-top">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
          </div>
        </div>
        <div class="card">
          <img src="/resources/images/kirby3.jpg" class="card-img-top">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>
            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
          </div>
        </div>
      </div>
      <!-- end of Card Deck -->
    </div>

    <!-- Jumbotron -->
    <div class="jumbotron container mt-5">
      <h1 class="display-4">Hello, world!</h1>
      <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
      <hr class="my-4">
      <p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
      <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
    </div>
    <!-- end of Jumbotron -->
    
    <%-- include bottomFooter.jsp --%>
    <jsp:include page="/WEB-INF/views/include/bottomFooter.jsp"/>
    
	<%-- include javascript.jsp --%>
	<jsp:include page="/WEB-INF/views/include/javascript.jsp"/>

</body>
</html>