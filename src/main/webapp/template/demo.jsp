<div class="col-md-9">
	<div class="row">
		<div class="col-centered col-md-12">
			<table id="mytable" class="table table-bordred table-striped">
				<thead>
					<th><input type="checkbox" id="checkall" /></th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Address</th>
					<th>Email</th>
					<th>Contact</th>
					<th>Edit</th>
					
					<th>Delete</th>
				</thead>
				
				<tbody>
				
					<tr>
						<td><input type="checkbox" class="checkthis" /></td>
						<td>Mohsin</td>
						<td>Irshad</td>
						<td>CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan</td>
						<td>isometric.mohsin@gmail.com</td>
						<td>+923335586757</td>
						<td><p data-placement="top" data-toggle="tooltip" title="Edit"><button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-target="#edit" ><span class="glyphicon glyphicon-pencil"></span></button></p></td>
						<td><p data-placement="top" data-toggle="tooltip" title="Delete"><button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" ><span class="glyphicon glyphicon-trash"></span></button></p></td>
					</tr>
					
					<tr>
						<td><input type="checkbox" class="checkthis" /></td>
						<td>Mohsin</td>
						<td>Irshad</td>
						<td>CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan</td>
						<td>isometric.mohsin@gmail.com</td>
						<td>+923335586757</td>
						<td><p data-placement="top" data-toggle="tooltip" title="Edit"><button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-target="#edit" ><span class="glyphicon glyphicon-pencil"></span></button></p></td>
						<td><p data-placement="top" data-toggle="tooltip" title="Delete"><button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" ><span class="glyphicon glyphicon-trash"></span></button></p></td>
					</tr>
				
				</tbody>  
			</table>
		</div>
	</div>
 </div>


<div class="row">
	<div class="col-centered col-md-5">
		<form action="/action_page.php">
			<div class="form-group">
				<label for="email">Email address:</label>
				<input type="email" class="form-control" id="email">
			</div>
			
			<div class="form-group">
				<label for="pwd">Password:</label>
				<input type="password" class="form-control" id="pwd">
			</div>
			
			<div class="checkbox">
				<label><input type="checkbox"> Remember me</label>
			</div>
			
			<button type="submit" class="btn btn-success">Submit</button>
		</form>
	</div>
</div>