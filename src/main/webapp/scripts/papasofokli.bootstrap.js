function updateValidation(fieldName, inputControlGroupId, error, warning, success, helpText) {
	if (error) {
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('success');
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('warning');
		$('#' + fieldName + 'Control' + inputControlGroupId).addClass('error');
	} 
	else if (warning){
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('success');
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('error');
		$('#' + fieldName + 'Control' + inputControlGroupId).addClass('warning');
	} 
	else if(success){
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('warning');
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('error');
		$('#' + fieldName + 'Control' + inputControlGroupId).addClass('success');
	}
	else {
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('warning');
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('error');
		$('#' + fieldName + 'Control' + inputControlGroupId).removeClass('success');
	}
	
	$('#' + fieldName + 'Help' + inputControlGroupId).html(helpText);
}

function buttonEnableDisable(id, disabled){
	if(disabled){ 
		$('#' + id).attr('disabled', 'disabled');
	}
	else{
		$('#' + id).removeAttr('disabled');
	}
		
}

function contactInfoDeleteUpdate(id, deleted){
	var buttonText = deleted ? ' Undo' : ' Delete';
	var icon = deleted ? 'icon-share-alt' : 'icon-trash';
	$('#contactInfoDeleteButton' + id).html('<i class="' + icon + ' icon-white"></i>' + buttonText);
	if(deleted){
		$('#contactInfoControl' + id + ' > input').attr('disabled', '');
		$('#contactInfoControl' + id + ' > select').attr('disabled', '');
	}
	else{
		$('#contactInfoControl' + id + ' > input').removeAttr('disabled');
		$('#contactInfoControl' + id + ' > select').removeAttr('disabled');
	}
}
