<template>
	<div class="container">
		<div class="input-group" style="margin-bottom: 10px;">
			<input type="text" class="form-control" placeholder="할일을 입력하세여" v-model="name" @keyup.enter="addTodo(name)">
			<span class="input-group-btn">
				<button class="btn btn-default" type="button" @click="addTodo(name)">추가</button>
			</span>
		</div>
		<ul class="nav nav-tabs nav-pills nav-fill">
			<li v-for="filter in filterList" class="nav-item">
				<a @click="changeFilter(filter)" v-model="selected" class="nav-link" :class="{'active':selected==filter}">{{filter}}</a>
			</li>
		</ul>
		<ul class="list-group mt-2">
			<li class="list-group-item" v-bind:class="{'list-group-item-selected':todo.completed}" v-for="(todo,index) in filteredTodos" :key="todo.id" :checked="{'checked':todo.completed}">
				<input type="checkbox" :id="todo.id" :value="todo.name" v-model="todo.completed">
				<label v-bind:for="todo.id">{{todo.name}}</label>
				<font-awesome-icon icon="trash" size="2x" class="float-right text-danger" @click="deleteTodo(todo.id)"/>
			</li>
		</ul>
	</div>
</template>

<script>
	const URI = "https://localhost:8080/todos"
	var todoFilters= {
		completed: function(todos) {
			return todos.filter(function(todo){
				return todo.completed
			})
		},
		active: function(todos) {
			return todos.filter(function(todo){
				return !todo.completed
			})
		},
		all: function(todos) {
			return todos
		}
	}
	export default {
		name:'TodoPage',
		data() {
			return {
				name: null,
				selected: 'all',
				filterList: ['all','active','completed'],
				todos: []
			}
		},
		mounted() {
			this.getTodos()
		},
		computed: {
			filteredTodos: function() {
				return todoFilters[this.selected](this.todos)
			}
		},
		methods: {
			getTodos() {
				var vm = this;
				this.$http.get(URI)
						.then((result)=>{
							console.log(result)
							vm.todos = result.data
						})
			},
			addTodo(name) {
				if(name!=null){
					var newtodo = {name:name, completed: false}
					this.todos.push(newtodo)
					this.$http.post(URI,newtodo)
							.then((result)=>{
								console.log(result);
							})
					this.name=null
				}
			},
			deleteTodo(id) {
				var idx = this.todos.findIndex(obj=>obj.id==id)
				if(idx != -1) {
					this.todos.splice(idx,1)
					this.$http.delete(URI+"/"+id)
				}
			},
			changeFilter(selected) {
				this.selected=selected
			}
		}
	}
</script>

<style>
	.nav-pills .nav-link.active, .nav-pills .show>.nav-link {
		background-color: #71E4F2;
	}
	.list-group-item-selected > label {
		color: grey;
		text-decoration: line-through;
	}
</style>