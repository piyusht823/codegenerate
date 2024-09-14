import { useEffect, useState } from "react";
import axios from "axios";
import { Bar } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const Graph = () => {
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: []
  });

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.get("http://localhost:8082/api/languages/max-lines");
      const data = response.data;

      const labels = data.map((item) => item.language);
      const totalLines = data.map((item) => item.totalLines);

      setChartData({
        labels,
        datasets: [
          {
            label: "Total Lines of Code",
            data: totalLines,
            backgroundColor: "rgba(75, 192, 192, 0.6)"
          }
        ]
      });
    };

    fetchData();
  }, []);

  return (
    <div>
      <h2>Language vs. Lines of Code</h2>
      <Bar
        data={chartData}
        options={{
          responsive: true,
          plugins: {
            legend: {
              position: "top"
            },
            title: {
              display: true,
              text: "Total Lines of Code per Language"
            }
          }
        }}
      />
    </div>
  );
};

export default Graph;
