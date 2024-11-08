    function ajaxCall() {
        this.send = function(data, url, method, success, type) {
          type = type||'json';
          
          var successRes = function(data) {
              success(data);
          }

          var errorRes = function(e) {
              console.log(e);
              alert("Error found \nError Code: "+e.status+" \nError Message: "+e.statusText);
          }
            $.ajax({
                url: url,
                type: method,
                data: data,
                success: successRes,
                error: errorRes,
                dataType: type,
                timeout: 60000
            });

          }

        }

    
 // Second Calling methods    
function locationInfo() {
	
	
	
    var rootUrl = "http://iamrohit.in/lab/php_ajax_country_state_city_dropdown/apiv1.php";
    var call = new ajaxCall();
    this.getCities = function(id) {
    	
        $(".cities option:gt(0)").remove();
        var url = rootUrl+'?type=getCities&stateId=' + id;
        var method = "post";
        var data = {};
        $('.cities').find("option:eq(0)").html("Please wait..");
        call.send(data, url, method, function(data) {
            $('.cities').find("option:eq(0)").html("Select City");
            if(data.tp == 1){
                $.each(data['result'], function(key, val) {
                    var option = $('<option />');
                    option.attr('value', key+","+val).text(val);
                    $('.cities').append(option);
                });
                $(".cities").prop("disabled",false);
            }
            else{
                 alert(data.msg);
            }
        });
    };

    this.getStates = function(id) {
    	
        $(".states option:gt(0)").remove(); 
        $(".cities option:gt(0)").remove(); 
        var url = rootUrl+'?type=getStates&countryId=' + id;
        var method = "post";
        var data = {};
        $('.states').find("option:eq(0)").html("Please wait..");
        call.send(data, url, method, function(data) {
            $('.states').find("option:eq(0)").html("Select State");
            if(data.tp == 1){
            	
                $.each(data['result'], function(key, val) {
                    var option = $('<option />');
                    option.attr('value', key+","+val).text(val);
                    $('.states').append(option);
                });
                
                $(".states").prop("disabled",false);
            }
            else{
                alert(data.msg);
            }
        }); 
    };

    this.getCountries = function() {
    	
    	
    	
        var url = rootUrl+'?type=getCountries';
        var method = "post";
        var data = {};
      //  $('.countries').find("option:eq(0)").html("Please wait..");
        
        call.send(data, url, method, function(data) {
        //    $('.countries').find("option:eq(0)").html("Select Country");
            console.log(data);
            if(data.tp == 1){
                $.each(data['result'], function(key, val) {
                    var option = $('<option />');
                    if(val=='India'){
                    option.attr('value',key+","+val).text(val);
                      $('.countries').append(option);
                      
                      //   alert("The Country values Append ???? \t"+$('.countries').append(option));
                    }
                  /*  option.attr('value', key).text(val);
                    $('.countries').append(option);*/
                });
                $(".countries").prop("disabled",false);
            }
            else{
                alert(data.msg);
            }
        }); 
    };

}

// Call the First Methods 
$(function() {
	
var loc = new locationInfo();
loc.getCountries();

loc.getStates(101);


/* Note: Read first After change it 
 * 
 * The country values bring Drop down on change select event in option using this method  
 * 
 * 
 $(".countries").on("change", function(ev) {
	 alert("The Country Selections ????");
        var countryId = $(this).val();
        alert("The Country Selections ????"+countryId);
        var result= countryId.split(",");
        if(countryId != ''){
        loc.getStates(result[0]);
        }
        else{
            $(".states option:gt(0)").remove();
        }
    });
    
    */
 
 
 
 
 $(".states").on("change", function(ev) {
        var stateId = $(this).val();
        var result= stateId.split(",");
        if(stateId != ''){
        	
        loc.getCities(result[0]);
        }
        else{
            $(".cities option:gt(0)").remove();
        }
    });
});

