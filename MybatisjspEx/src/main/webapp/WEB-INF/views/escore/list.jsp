<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/forms.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/board.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/paginate.css" type="text/css">

<style type="text/css">
  .body-container { margin: 30px auto; width: 700px; }
</style>

</head>
<body>

<div class="body-container">
	<div class="body-title">
	    <h2><span>|</span> 성적처리</h2>
	</div>
	
	<table class="table">
		<tr>
			<td align="left" width="50%">
				${dataCount}개(${page}/${total_page} 페이지)
			</td>
			<td align="right">
				<button type="button" onclick="location.href='${pageContext.request.contextPath}/escore/excel';" class="btn">EXCEL</button>
				<button type="button" onclick="location.href='${pageContext.request.contextPath}/escore/pdf';" class="btn">PDF</button>
				<button type="button" class="btn" onclick="printScore();">Print</button>
			</td>
		</tr>
	</table>
		
	<table class="table table-list table-border">
		<thead>
			<tr>
				<th width="70">학번</th>
				<th width="100">이름</th>
				<th width="100">생년월일</th>
				<th width="60">국어</th>
				<th width="60">영어</th>
				<th width="60">수학</th>
				<th width="60">총점</th>
				<th width="60">평균</th>
				<th>변경</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="dto" items="${list}">
			<tr>
				<td>${dto.hak}</td>
				<td>${dto.name}</td>
				<td>${dto.birth}</td>
				<td>${dto.kor}</td>
				<td>${dto.eng}</td>
				<td>${dto.mat}</td>
				<td>${dto.tot}</td>
				<td>${dto.ave}</td>
				<td>
					<input type="button" value="수정" onclick="updateScore('${dto.hak}')" class="btn">
					<input type="button" value="삭제" onclick="deleteScore('${dto.hak}')" class="btn">
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="page-navigation">
		${dataCount == 0 ? "등록된 정보가 없습니다." : paging}
	</div>
	
	<table class="table">
		<tr>
			<td width="100">
				<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/escore/main';">새로고침</button>
			</td>
			<td align="center">
				<form name="searchForm">
					<select name="schType" class="form-select">
						<option value="hak" ${schType=="hak" ? "selected":""}>학번</option>
						<option value="name" ${schType=="name" ? "selected":""}>이름</option>
						<option value="birth" ${schType=="birth" ? "selected":""}>생년월일</option>
					</select>
					<input type="text" name="kwd" value="${kwd}" class="form-control">
					<button type="button" class="btn" onclick="searchList();">검색</button>
				</form>
			</td>
			<td align="right" width="100">
				<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/escore/write';">등록하기</button>
			</td>
		</tr>
	</table>	
</div>

<script type="text/javascript">
//검색 키워드 입력란에서 엔터를 누른 경우 서버 전송 막기 
window.addEventListener('load', () => {
	const inputEL = document.querySelector('form input[name=kwd]'); 
	inputEL.addEventListener('keydown', function (evt) {
	    if(evt.key === 'Enter') {
	    	evt.preventDefault();
	    	
	    	searchList();
	    }
	});
});

function searchList() {
	const f = document.searchForm;
	if(! f.kwd.value.trim()) {
		return;
	}
	
	// form 요소는 FormData를 이용하여 URLSearchParams 으로 변환
	const formData = new FormData(f);
	let requestParams = new URLSearchParams(formData).toString();
	
	/*
	let params = {
		schType:f.schType.value, kwd:f.kwd.value
	};
	// 자바스트립트 객체를 queryString(이름1=값1&이름2=값2)로 변환
	const requestParams = new URLSearchParams(params).toString();
	*/
	
	let url = '${pageContext.request.contextPath}/escore/main';
	location.href = url + '?' + requestParams;
}
</script>

<script type="text/javascript">
function updateScore(hak) {
	let url = '${pageContext.request.contextPath}/escore/update?hak=' + hak + '&page=${page}';
	location.href = url;
}

function deleteScore(hak) {
	let url = '${pageContext.request.contextPath}/escore/delete?hak=' + hak + '&page=${page}';
	
	if(confirm('자료를 삭제 하시겠습니까 ?')) {
		location.href = url;
	}	
}
</script>

</body>
</html>