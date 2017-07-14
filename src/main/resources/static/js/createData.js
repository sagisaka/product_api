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

function create(){//	アップロードボタンを押下した
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
	name = escape_html($("#name").val());
	introduction = escape_html($("#introduction").val());
	price = escape_html($("#price").val()+"円");
	var formData = new FormData($('#form').get()[0]);
	formData.append("name",name);
	formData.append("introduction",introduction);
	formData.append("price",price);
	$.ajax({
		url:'/api/product/',
		method:'post',
		data:formData,
		processData:false,
		contentType:false,
		cache: false,
		dataType: 'json',
		success: function(json) {
			alert("画像を更新するためOK押下後、10秒間お待ちください！");
			sleep(10000);
			document.location = "/";
		},
		error: function() {         // HTTPエラー時
			alert("Server Error. Pleasy try again later.");
		},
		complete: function() {
			button.attr("disabled", false);
		}
	})
}

function sleep(waitMsec) {
	var startMsec = new Date();
	while (new Date() - startMsec < waitMsec);
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
				//既存のプレビューを削除
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
