<template>
	<div class="container">
		<div class="input-group" style="margin-bottom: 10px;">
			<input type="text" class="form-control" placeholder="할일을 입력하세여" v-model="name" @keyup.enter="addTodo(name)">
			<span class="input-group-btn">
				<button class="btn cyan lighten-2" type="button" @click="addTodo(name)">추가</button>
			</span>
		</div>
		<ul class="nav nav-tabs nav-pills nav-fill">
			<li v-for="filter in filterList" class="nav-item">
				<a @click="changeFilter(filter)" v-model="selected" class="nav-link" :class="{'active':selected==filter}">{{filter}}</a>
			</li>
		</ul>
		<ul class="list-group mt-2">
			<li class="list-group-item" v-bind:class="{'list-group-item-selected':todo.completed}" v-for="(todo,index) in filteredTodos" :key="todo.id">
				<!-- <span @click="toggleChecked(todo)" style="cursor:pointer">
					<i class="fa" aria-hidden="true" v-model="todo.completed" :class="checkedIcon(todo.completed)"></i>
				</span> -->
				<input type="checkbox" :id="todo.id" :value="todo.name" v-model="todo.completed">
				<label v-bind:for="todo.id">{{todo.name}}</label>
				<button class="btn btn-pill btn-sm" :class="{'aqua-gradient':!todo.completed}" v-for="(tag,idx) in todo.tags" @click="deleteTag(todo,tag)">{{tag.name}}</button>
				<span v-show="isEditing[index].tag==false || isTagFull(todo)">
					<button class="btn btn-sm cyan lighten-1 white-text" @click="isEditing[index].tag=true"><fa icon="plus" size="lg"></fa></button>
				</span>
				<span v-show="isEditing[index].tag== true">
					<column class="col-sm-1">
						<input v-model="editedTag" @keyup.enter="isEditing[index].tag=false; addTag(todo)">
					</column>
				</span>
				<span @click="deleteTodo(todo)" style="cursor:pointer">
					<fa icon="trash-o" size="2x" class="float-right orange-text" ></fa>	
				</span>
			</li>
		</ul>
	</div>
</template>

<script>
	import { Btn, Fa,Column } from 'mdbvue'
	const URI = "https://192.168.20.23:8000"
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
		components: {
			Btn,
			Fa,
			Column
		},
		data() {
			return {
				name: null,
				selected: 'all',
				filterList: ['all','active','completed'],
				isEditing: [],
				editedTag: null,
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
				this.$http.get(URI+"/todos")
						.then((result)=>{
							vm.todos = result.data
							for(var i=0;i<result.data.length;i++){
								var obj = {todo:false, tag:false}
								vm.isEditing.push(obj)
							}
						})
			},
			addTodo(str) {
				if(str.length > 0){
					var hashtags = str.match(/#[0-9a-zA-Z가-힣]+[a-zA-Z0-9가-힣_+]*/gm)
					console.log(hashtags)
					var tags = (hashtags)? hashtags.map(i=>i.substring(1)): [];
					console.log(tags)
					var name = "";
					for(var i=0;i<str.length;i++) {
						var c= str.charAt(i);
						if(c==='#') {
							while(str.charAt(i)!=' '&&i<str.length)
								++i;
						} else {
							name = name + c;
						}
					}
					var newtodo = {name:name, completed: false, tags:tags}
					this.$http.post(URI+"/todos",newtodo)
							.then((result)=>{
								this.todos.push(result.data);
							})
					this.name=null
				}
			},
			deleteTodo(todo) {
				var idx = this.todos.indexOf(todo)
				if(idx != -1) {
					this.todos.splice(idx,1)
					this.$http.delete(URI+"/todos/"+todo.id)
				}
			},
			changeFilter(selected) {
				this.selected=selected
			},
			checkedIcon: function(iscompleted){
				return {
					'fa-check': iscompleted==true,
					'fa-minus': iscompleted==false					
				}
			},
			toggleChecked: function(todo) {
				var idx = this.todos.indexOf(todo)
				if(idx != -1) {
					this.todos[idx].iscompleted = !this.todos[idx].iscompleted
				}
			},
			deleteTag: function(todo,tag) {
				var todoidx = this.todos.indexOf(todo)
				if(todoidx != -1) {
					var tagidx =this.todos[todoidx].tags.indexOf(tag)
					this.todos[todoidx].tags.splice(tagidx,1)
					this.$http.delete(URI+"/tags/"+tag.id)
				}
			},
			isTagFull: function(todo) {
				if(todo.tags.length >= 5)
					return 'hidden'
			},
			addTag: function(todo) {
				var newtag = {todoid:todo.id, name:this.editedTag}
				var todoidx = this.todos.indexOf(todo)
				if(todoidx != -1) {
					this.$http.post(URI+"/tags",newtag)
							.then((result)=>{
								this.todos[todoidx].tags.push(result.data)
							})
					this.editedTag = null;
				}
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