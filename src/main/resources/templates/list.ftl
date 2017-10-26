<div class="generic-container">
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<span class="lead">User </span>
		</div>
		<div class="panel-body">
			<div class="formcontainer">
				<div class="alert alert-success" role="alert" ng-if="data.successMessage">{{data.successMessage}}
				</div>
				<div class="alert alert-danger" role="alert" ng-if="data.errorMessage">{{data.errorMessage}}
				</div>
				<form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
					<fieldset ng-disabled="data.buttonRunDisable">

						<div class="row">
							<div class="form-group col-md-12"
								ng-class="{'has-error': myForm.name.$dirty && myForm.name.$error.required}">
								<label class="col-md-2 control-lable" for="llamada">Nombre: </label>
								<div class="col-md-7">
									<input type="text" name="name" ng-model="data.properties.name"
										id="name" class="form-control input-sm" required />
								</div>
								<span ng-show="myForm.name.$dirty && myForm.name.$error.required">campo requerido</span>
							</div>
						</div>

						<div class="row">
							<div class="form-group col-md-12"
								ng-class="{'has-error': myForm.dna.$dirty && myForm.dna.$error.required}">
								<label class="col-md-2 control-lable" for="llamada">DNA: </label>
								<div class="col-md-7">
									<input type="text" name="dna" ng-model="data.properties.dna"
										id="dna" class="form-control input-sm" required />
								</div>
								<span ng-show="myForm.dna.$dirty && myForm.dna.$error.required">campo requerido</span>
							</div>
						</div>


						<div class="row">
							<div class="form-actions floatRight">
								<input type="button" ng-click="submit()" value="Disparar Llamadas"
									class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine" />
								<button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm"
									ng-disabled="myForm.$pristine">view stats</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<span class="lead">List of Users </span>
		</div>
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ID Mutante</th>
							<th>Nombre</th>
							<th>Es Mutante</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="d in data.registroLlamada">
							<td>{{d.id}}</td>
							<td>{{d.duracion}}</td>
							<td>{{d.operador.nombre}}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>