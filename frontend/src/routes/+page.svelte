<script lang="ts">
	// get data from backend
	import { onMount } from 'svelte';

	// create state
	let messages = $state([] as { id: string; text: string }[]);
	let text = $state('');
	let isEditing = $state(false);
	let editingId = $state('');
	let editingText = $state('');

	const fetchMessages = async () => {
		const res = await fetch('http://localhost:8080/messages');
		const data = await res.json();
		messages = data;
	};

	const addMessage = async (text: string) => {
		const res = await fetch('http://localhost:8080/messages', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: text
		});
		const data = await res.json();
		await fetchMessages();
	};

	const deleteMessage = async (id: string) => {
		const res = await fetch(`http://localhost:8080/messages/${id}`, {
			method: 'DELETE'
		});
		await fetchMessages();
	};

	const updateMessage = async (id: string, text: string) => {
		const res = await fetch(`http://localhost:8080/messages/${id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: text
		});
		await fetchMessages();
	};
	onMount(fetchMessages);
</script>

<div class="flex">
	<div class="border">
		<h1 class="border-b font-bold">Message CRUD</h1>

		<form
			onsubmit={(e) => {
				addMessage(text);
			}}
			class="flex gap-3 p-1"
		>
			<input
				type="text"
				class="w-full border"
				onchange={(e) => {
					if (e.target) {
						text = (e.target as HTMLInputElement).value;
					}
				}}
				required
				placeholder="Enter message"
			/>
			<button class="bg-green-300 p-1" type="submit">Add</button>
		</form>

		<table>
			<thead>
				<tr class="font-bold">
					<td>Id</td>
					<td>Text</td>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				{#each messages as message}
					<tr>
						<td class="p-1">{message.id}</td>
						<td class="min-w-64 p-1">{message.text}</td>
						<td class="p-1">
							<button
								class="bg-red-300 p-1"
								onclick={() => {
									isEditing = false;
									deleteMessage(message.id);
								}}>Delete</button
							>
						</td>
						<td class="p-1">
							<button
								class="bg-blue-300 p-1"
								onclick={() => {
									isEditing = true;
									editingId = message.id;
									editingText = message.text;
								}}>Edit</button
							>
						</td>
					</tr>
				{/each}
			</tbody>
		</table>
		<button
			class="w-full bg-blue-300 p-1"
			onclick={() => {
				fetchMessages();
				isEditing = false;
			}}>Reload</button
		>
		{#if isEditing}
			<div class="border-t">
				<h1>Editing message with id: {editingId}</h1>
			</div>
			<form
				onsubmit={(e) => {
					updateMessage(editingId, editingText);
					isEditing = false;
					editingId = '';
				}}
				class="flex gap-3 p-1"
			>
				<input
					type="text"
					class="w-full border"
					oninput={(e) => {
						if (e.target) {
							editingText = (e.target as HTMLInputElement).value;
						}
					}}
					required
					value={editingText}
					placeholder="Enter message"
				/>
				<button class="bg-green-300 p-1" type="submit">Update</button>
			</form>
			<button
				class="w-full bg-red-300 p-1"
				onclick={() => {
					isEditing = false;
					editingId = '';
				}}>Cancel</button
			>
		{/if}
	</div>
</div>
