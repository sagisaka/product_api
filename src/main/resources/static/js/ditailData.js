function escape_html (string) {
	if(typeof string !== 'string') {
		return string;
	}
	return string.replace(/[&'`"<>]/g, function(match) {
		return {
			'&': '&amp;',
			"'": '&#x27;',
			'`': '&#x60;',
			'"': '&quot;',
			'<': '&lt;',
			'>': '&gt;',
		}[match]
	});
}

function updata() {
	// 多重送信を防ぐため通信完了までボタンをdisableにする
	var button = $(this);
	button.attr("disabled", true);
	//処理に不足があるか
	if($("#name").val() == 0){
		alert('商品名を入力してください');
		return;
	}else if($("#introduction").val().length==0){
		alert('紹介を入力してください');
		return;
	}else if($("#price").val().length==0){
		alert('価格を入力してください');
		return;
	}else if($("#file").val().length==0){
		alert('fileを選択してください');
		return;
	}
	// 各フィールドから値を取得してJSONデータを作成
	name = escape_html($("#name").val());
	introduction = escape_html($("#introduction").val());
	price = escape_html($("#price").val()+"円");
	var formData = new FormData($('#form').get()[0]);
	formData.append("name",name);
	formData.append("introduction",introduction);
	formData.append("price",price);

	// 通信実行
	$.ajax({
		type:"POST",               
		url:"/api/product/"+$("#s").text(),       
		data:formData,
		processData:false,
		contentType:false,
		cache: false,
		dataType: 'json',

		success: function(json_data) {
			location.reload();
		},
		error: function() {         // HTTPエラー時
			alert("Server Error. Pleasy try again later.");
		},
		complete: function() {      
			button.attr("disabled", false);  
		}
	});
}

function deleteData() {

	var button = $(this);
	button.attr("disabled", true);
	if(window.confirm('この商品のデータを消しますか？')){

		$.ajax({
			type:"DELETE",              
			url:"/api/product/"+$("#s").text(),       
			success: function() {   
				document.location = "/";		
			},
			error: function() {         // HTTPエラー時
				alert("Server Error. Pleasy try again later.");
			},
			complete: function() {      
				button.attr("disabled", false);  
			}
		});
	}
}

$(function(){
	//画像ファイルプレビュー表示のイベント追加 fileを選択時に発火するイベントを登録
	$('form').on('change', 'input[type="file"]', function(e) {
		var file = e.target.files[0],
		reader = new FileReader(),
		$preview = $(".preview");
		t = this;
		// 画像ファイル以外の場合は何もしない
		if(file.type.indexOf("image") < 0){
			return false;
		}
		// ファイル読み込みが完了した際のイベント登録
		reader.onload = (function(file) {
			return function(e) {
				$preview.empty();
				$preview.append($('<img>').attr({
					src: e.target.result,
					width: "300px",
					class: "preview",
					title: file.name
				}));
			};
		})(file);

		reader.readAsDataURL(file);
	});
});