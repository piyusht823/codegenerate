"use client"
import ChatUI from "./chatui";
import Graph from "./graph";
import styles from "./page.module.css";

export default function Home() {
  return (
    <div className={styles.container}>
      <div className={styles.chatSection}>
        <ChatUI />
      </div>
      <div className={styles.graphSection}>
        <Graph />
      </div>
      <div className={styles.footer}>
        <h3>@GPTeam</h3>
      </div>
    </div>
  );
}
