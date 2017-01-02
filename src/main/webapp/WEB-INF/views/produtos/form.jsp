<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<f:form action="${ s:mvcUrl('PC#gravar').build() }" method="POST" commandName="produto" enctype="multipart/form-data">
		<div>
			<label>Título</label>
			<f:input path="titulo" />
			<f:errors path="titulo"/>
		</div>
		<div>
			<label>Descrição</label>
			<f:textarea rows="10" cols="20" path="descricao"/>
			<f:errors path="descricao"/>
		</div>
		<div>
			<label>Páginas</label>
			<f:input path="paginas" />
			<f:errors path="paginas"/>
		</div>
		<div>
			<label>Data de Lançamento</label>
			<f:input path="dataLancamento" />
			<f:errors path="dataLancamento"/>
		</div>

		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div>
				<label>${tipoPreco}</label>
				<f:input path="precos[${status.index}].valor"/>
				<f:hidden path="precos[${status.index}].tipo" value="${tipoPreco}"/>
			</div>
		</c:forEach>

		<div>
			<label>Sumário</label>
			<input name="sumario" type="file">
		</div>

		<f:button type="submit">Cadastrar</f:button>
	</f:form>

</body>
</html>