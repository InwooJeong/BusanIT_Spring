<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1>전체 회원정보 가져오기</h1>
	<hr>
	
	<button type="button" id="btn">버튼</button>
	
	<table border="1">
		<thead>
			<tr>
				<th>아이디</th>
				<th>이  름</th>
				<th>성  별</th>
				<th>이메일</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4">버튼을 클릭하세요.</td>
			</tr>
		</tbody>
	</table>
	
	<script src="/resources/js/jquery-3.6.0.js"></script>
	<script type="text/javascript">
		$('button#btn').on('click',function(){

			//ajax 함수 호출
			$.ajax({
				url : '/api/members.json',
				method : 'GET',
				success : function(data) {
					console.log(typeof data); //object
					console.log(data); // [{},{},....]
					
					showData(data);
				},
				error : function(request, status, error){
					alert('code : ' + request.status + '\n message : ' + request.responseText + '\n error : '+error);
				}
			});
			
		});
		
		function showData(array){
			let str = '';
			
			if(array != null && array.length > 0){
				let member = array.member;
				
				for(let member of array){
					str += `
						<tr>
							<td>\${member.id}</td>
							<td>\${member.name}</td>
							<td>\${(member.gender=='M')?'남':'여'}</td>
							<td>\${member.email}</td>
						</tr>
					`;
				}
				
			}else{ //array == null || array.length == 0
				let id = $('#id').val();

				str = `
					<tr>
						<td colspan="4">데이터가 없습니다.</td>
					</tr>
				`;
			}
			
			$('table > tbody').html(str);
			
		}
	</script>
	
</body>
</html>