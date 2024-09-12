<template>
  <div class="container">
    <h1 class="title">Person API Client</h1>

    <!-- Input untuk ID (untuk GET, DELETE) -->
    <div class="form-group">
      <input
        v-model="personId"
        class="input"
        type="text"
        placeholder="Enter Person ID (optional for GET, required for DELETE)"
      />
    </div>

    <!-- Input untuk method -->
    <div class="form-group">
      <select v-model="method" class="select">
        <option value="GET">GET</option>
        <option value="POST">POST</option>
        <option value="DELETE">DELETE</option>
      </select>
    </div>

    <!-- Input untuk data (POST) -->
    <div v-if="method === 'POST'" class="form-group">
      <input v-model="personData.name" class="input" placeholder="Enter Name" />
      <input
        v-model="personData.age"
        class="input"
        type="number"
        placeholder="Enter Age"
      />
    </div>

    <!-- Tombol untuk kirim request -->
    <button @click="sendRequest" class="button">Send Request</button>

    <!-- Tampilan hasil response -->
    <div v-if="responseData" class="response-container">
      <h3 class="response-title">Response:</h3>
      <pre class="response-data">{{ responseData }}</pre>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      method: "GET", // Default method
      personId: "", // ID untuk GET, DELETE
      personData: { name: "", age: null }, // Data untuk POST
      responseData: null, // Response dari server
    };
  },
  methods: {
    async sendRequest() {
      const apiUrl = `/api/persons${this.personId ? `/${this.personId}` : ""}`;
      let response;

      try {
        if (this.method === "GET") {
          response = await axios.get(apiUrl);
        } else if (this.method === "POST") {
          response = await axios.post(apiUrl, this.personData);
        } else if (this.method === "DELETE") {
          response = await axios.delete(apiUrl);
        }

        this.responseData = response.data;
      } catch (error) {
        this.responseData = error.response
          ? error.response.data
          : error.message;
      }
    },
  },
};
</script>

<style scoped>
/* Container */
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
}

/* Title */
.title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

/* Form input and select */
.form-group {
  margin-bottom: 15px;
}

.input,
.select {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.input:focus,
.select:focus {
  border-color: #66afe9;
  outline: none;
}

/* Button */
.button {
  display: inline-block;
  width: 100%;
  padding: 10px 15px;
  background-color: #007bff;
  color: #fff;
  font-size: 16px;
  text-align: center;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.button:hover {
  background-color: #0056b3;
}

/* Response */
.response-container {
  margin-top: 20px;
  padding: 10px;
  background-color: #f0f0f0;
  border-left: 5px solid #007bff;
  border-radius: 5px;
}

.response-title {
  font-size: 18px;
  color: #333;
}

.response-data {
  white-space: pre-wrap;
  word-wrap: break-word;
  background-color: #fff;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}
</style>
