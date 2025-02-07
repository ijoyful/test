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

<style type="text/css">
  .body-container { margin: 30px auto; width: 550px; }
  
  .info-box { padding: 10px 0; text-align: center; color: blue; }
</style>

<script type="text/javascript">
function isValidScore(data) {
    let regexp = /^(\d+)$/;
    if(! regexp.test(data)) {
        return false;
    }
    
	let s = parseInt(data);
	
	return s>=0 && s<=100 ? true : false;
}

function sendOk() {
	const f = document.scoreForm;
	
	if(! f.hak.value.trim()) {
        alert("필수 입력 사항 입니다. !!!");
        f.hak.focus();
        return;
	}

	if(! f.name.value.trim()) {
        alert("필수 입력 사항 입니다. !!!");
        f.name.focus();
        return;
	}
	
    if(! f.birth.value) {
        alert("날짜 형식이 유효하지 않습니다. ");
        f.birth.focus();
        return;
	}
	
    if(! isValidScore(f.kor.value)) {
		alert("점수는 0~100 사이만 가능합니다. ");
		f.kor.focus();
		return;
    }
    
    if(! isValidScore(f.eng.value)) {
        alert("점수는 0~100 사이만 가능합니다. ");
        f.eng.focus();
        return;
	}
    
    if(! isValidScore(f.mat.value)) {
        alert("점수는 0~100 사이만 가능합니다. ");
        f.mat.focus();
        return;
	}
	
	f.action = '${pageContext.request.contextPath}/escore/${mode}';
	
	f.submit();
}

window.addEventListener('DOMContentLoaded', () => {
	const dateELS = document.querySelectorAll('form input[type=date]');
	dateELS.forEach( inputEL => inputEL.addEventListener('keydown', e => e.preventDefault()) );
});
</script>
</head>
<body>

<div class="body-container">
	<div class="body-title">
	    <h2><span>|</span> 성적처리</h2>
	</div>

	<form name="scoreForm" action="" method="post">
		<table class="table table-border table-form">
			<tr>
				<td>학 번</td>
				<td>
					<input type="text" name="hak" class="form-control" maxlength="10" value="${dto.hak}"
							${mode=="update" ? "readonly":""}>
				</td>
			</tr>
			
			<tr>
				<td>이 름</td>
				<td>
					<input type="text" name="name" class="form-control" maxlength="10" value="${dto.name}">
				</td>
			</tr>
			
			<tr>
				<td>생년월일</td>
				<td>
					<input type="date" name="birth" class="form-control" value="${dto.birth}">
				</td>
			</tr>
			
			<tr>
				<td>국 어</td>
				<td>
					<input type="text" name="kor" class="form-control" maxlength="3" value="${dto.kor}">
				</td>
			</tr>
			
			<tr>
				<td>영 어</td>
				<td>
					<input type="text" name="eng" class="form-control" maxlength="3" value="${dto.eng}">
				</td>
			</tr>
			
			<tr>
				<td>수 학</td>
				<td>
					<input type="text" name="mat" class="form-control" maxlength="3" value="${dto.mat}">
				</td>
			</tr>
		</table>
		
		<table class="table">
			<tr>
				<td align="center" colspan="2">
					<button type="button" class="btn" onclick="sendOk();"> ${mode=='write' ? '등록완료':'수정완료'} </button> 
					<button type="reset" class="btn"> 다시입력 </button>
					<button type="button" class="btn"
						onclick="javascript:location.href='${pageContext.request.contextPath}/escore/main';"> ${mode=='write' ? '등록취소':'수정취소'} </button>
					<c:if test="${mode=='update'}">
						<input type="hidden" name="page" value="${page}">
					</c:if>
				</td>
			</tr>
		</table>
	</form>
	
	<div class="info-box">${message}</div>
	
</div>

</body>
</html>