<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
  <div class="container">
    <form>
      <div class="form-group">
        <input type="text" class="form-control" placeholder="Enter title" id="title">
      </div>

      <div class="form-group">
        <textarea class="form-control summernote" rows="5" id="content"></textarea>
      </div>


    </form>
    <button id="btn-save" class="btn btn-primary">글쓰기 완료</button> <%--json으로 요청을 할거여서 form 밖으로 뺌--%>

  </div>

</div>

<script>
  $('.summernote').summernote({
    tabsize: 2,
    height: 300
  });
</script>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>
