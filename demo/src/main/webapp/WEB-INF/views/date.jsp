<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html dir="ltr" lang="en-US">
   <head>
      <meta charset="UTF-8" />
      <title>A date range picker for Bootstrap</title>

      <!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" />-->

<!--       <link rel="stylesheet" type="text/css" media="all" href="daterangepicker.css" /> -->

<!--       <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script> -->

<!--       <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.1/moment.min.js"></script> -->

<!--       <script type="text/javascript" src="daterangepicker.js"></script> -->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <c:import url="/include.commonHeader.sp" charEncoding="UTF-8" />
	<title>Insert title here</title>
	<c:import url="/include.commonPlugin.sp" charEncoding="UTF-8" />
   </head>
   <body style="margin: 60px 0">

      <div class="container">

        <h1 style="margin: 0 0 20px 0">Configuration Builder</h1>
		<div>
			<button id="btn1" >값가져옥</button>
			<button id="btn2" >설정</button>
			<button id="btn3" >설정2</button>
		</div>
		<div id="reportrange" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
		    <i class="fa fa-calendar"></i>&nbsp;
		    <span></span> <i class="fa fa-caret-down"></i>
		</div>
			<input type="text" id="iptDR" class="form-control">
		<div>
		    
		</div>
        <div class="well configurator">
           
          <form>
          <div class="row">

            <div class="col-md-4">

              <div class="form-group">
                <label for="parentEl">parentEl</label>
                <input type="text" class="form-control" id="parentEl" value="" placeholder="body">
              </div>

              <div class="form-group">
                <label for="startDate">startDate</label>
                <input type="text" class="form-control" id="startDate" value="07/01/2015">
              </div>

              <div class="form-group">
                <label for="endDate">endDate</label>
                <input type="text" class="form-control" id="endDate" value="07/15/2015">
              </div>

              <div class="form-group">
                <label for="minDate">minDate</label>
                <input type="text" class="form-control" id="minDate" value="" placeholder="MM/DD/YYYY">
              </div>

              <div class="form-group">
                <label for="maxDate">maxDate</label>
                <input type="text" class="form-control" id="maxDate" value="" placeholder="MM/DD/YYYY">
              </div>

            </div>
            <div class="col-md-4">

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="autoApply"> autoApply
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="singleDatePicker"> singleDatePicker
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="showDropdowns"> showDropdowns
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="showWeekNumbers"> showWeekNumbers
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="showISOWeekNumbers"> showISOWeekNumbers
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="timePicker" checked="checked"> timePicker
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="timePicker24Hour"> timePicker24Hour
                </label>
              </div>

              <div class="form-group">
                <label for="timePickerIncrement">timePickerIncrement (in minutes)</label>
                <input type="text" class="form-control" id="timePickerIncrement" value="1">
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="timePickerSeconds"> timePickerSeconds
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="dateLimit"> dateLimit (with example date range span)
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="ranges" checked="checked"> ranges (with example predefined ranges)
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="locale"> locale (with example settings)
                </label>
                <label id="rtl-wrap">
                  <input type="checkbox" id="rtl"> RTL (right-to-left)
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="alwaysShowCalendars"> alwaysShowCalendars
                </label>
              </div>

            </div>
            <div class="col-md-4">

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="linkedCalendars" checked="checked"> linkedCalendars
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="autoUpdateInput" checked="checked"> autoUpdateInput
                </label>
              </div>

              <div class="checkbox">
                <label>
                  <input type="checkbox" id="showCustomRangeLabel" checked="checked"> showCustomRangeLabel
                </label>
              </div>

              <div class="form-group">
                <label for="opens">opens</label>
                <select id="opens" class="form-control">
                  <option value="right" selected>right</option>
                  <option value="left">left</option>
                  <option value="center">center</option>
                </select>
              </div>

              <div class="form-group">
                <label for="drops">drops</label>
                <select id="drops" class="form-control">
                  <option value="down" selected>down</option>
                  <option value="up">up</option>
                </select>
              </div>

              <div class="form-group">
                <label for="buttonClasses">buttonClasses</label>
                <input type="text" class="form-control" id="buttonClasses" value="btn btn-sm">
              </div>

              <div class="form-group">
                <label for="applyClass">applyClass</label>
                <input type="text" class="form-control" id="applyClass" value="btn-success">
              </div>

              <div class="form-group">
                <label for="cancelClass">cancelClass</label>
                <input type="text" class="form-control" id="cancelClass" value="btn-default">
              </div>
              
              <div class="form-group">
                <label for="iptFormat">format</label>
                <input type="text" class="form-control" id="iptFormat" value="YYYY.MM.DD">
              </div>

            </div>

          </div>
          </form>

        </div>

        <div class="row">

          <div class="col-md-4 col-md-offset-2 demo">
            <h4>Your Date Range Picker</h4>
            <center>
              <input type="text" id="config-demo" class="form-control">
            </center>
          </div>

          <div class="col-md-6">
            <h4>Configuration</h4>

            <div class="well">
              <textarea id="config-text" style="height: 300px; width: 100%; padding: 10px"></textarea>
            </div>
          </div>

        </div>

      </div>

      <style type="text/css">
      .demo { position: relative; }
      .demo i {
        position: absolute; bottom: 10px; right: 24px; top: auto; cursor: pointer;
      }
      </style>

      
<script type="text/javascript">
$.getScript(getContextPath() + "/resources/service/date.js");
</script>
   </body>
</html>
