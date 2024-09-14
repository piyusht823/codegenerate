// pages/api/languages/max-lines.js

import axios from 'axios';

export default async function handler(req, res) {
  try {
    const response = await axios.get('http://localhost:8082/api/gpt/api/languages/max-lines');
    res.status(200).json(response.data);
  } catch (error) {
    res.status(500).json({ error: 'Error fetching language data' });
  }
}
