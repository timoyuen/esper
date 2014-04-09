function findMatch(epl) {
	var valid_args = new Array();
	valid_args.push("CODE");
	valid_args.push("NAME");
	valid_args.push("TOPENPRICE");
	valid_args.push("YOPENPRICE");
	valid_args.push("CPRICE");
	valid_args.push("TMAXPRICE");
	valid_args.push("TMINPRICE");
	valid_args.push("DEALCOUNT");
	valid_args.push("DEALMONEY");
	valid_args.push("B1PRICE");
	valid_args.push("B2PRICE");
	valid_args.push("B3PRICE");
   	valid_args.push("B4PRICE");
   	valid_args.push("B5PRICE");
   	valid_args.push("S1PRICE");
   	valid_args.push("S2PRICE");
   	valid_args.push("S3PRICE");
   	valid_args.push("S4PRICE");
   	valid_args.push("S5PRICE");
	valid_args.push("B1COUNT");
	valid_args.push("B2COUNT");
	valid_args.push("B3COUNT");
	valid_args.push("B4COUNT");
	valid_args.push("B5COUNT");
	valid_args.push("S1COUNT");
	valid_args.push("S2COUNT");
	valid_args.push("S3COUNT");
	valid_args.push("S4COUNT");
	valid_args.push("S5COUNT");
	for (var i = 0; i < valid_args.length; i++) {
		var args_pattern = eval("/"+"\\$"+valid_args[i]+"=\\?"+"/g");
		var matches = epl.match(args_pattern);
		return matches;
		// if (matches)
		// 	count += matches.length;
	}
	// var count = 0;
	// for (var i = 0; i < valid_args.length; i++) {
	// 	var args_pattern = eval("/"+"\\"+valid_args[i]+"\\s"+"/g");
	// 	var matches = epl.match(args_pattern);
	// 	if (matches)
	// 		count += matches.length;
	// }
	// var args_DO = /\$/g;
	// var matches_DO = epl.match(args_DO);

	// var args_question_mark = /\?/g;
	// var matches_q_m = epl.match(args_question_mark);
	// if (matches_DO && matches_DO.length == count && matches_q_m.length == count) {
	// 	var args_pattern = /\$[A-Z0-9]+\s/g;
	// 	var matches = epl.match(args_pattern);
	// 	if (matches) {
	// 		for (var i = 0; i < matches.length; i++) {
	// 			matches[i] = $.trim(matches[i]);
	// 		}
	// 		return matches;
	// 	} else {
	// 		return false;
	// 	}
	// } else {
	// 	return false;
	// }
}