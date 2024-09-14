import axios from "axios";

export default async function handler(req, res) {
  if (req.method === "POST") {
    const { input } = req.body;

    try {
      // Call your Spring Boot backend API
      const response = await axios.post("http://localhost:8082/api/gpt/generate", { input });
      res.status(200).json(response.data);
    } catch (error) {
      res.status(500).json({ error: "Error generating response" });
    }
  }
}
