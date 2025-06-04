"use client";
import { useState } from "react";
// フォーム型、型きめ
type ToDoForm = {
  title: string;
  is_completed: boolean;
};

//初期値
const defaultForm: ToDoForm = {
  title: "",
  is_completed: false,
};

//コンポーネントの定義　フォームの初期化、プルダウンの初期化、送信値の設定
export default function ContactPage() {
  const [form, setForm] = useState<ToDoForm>(defaultForm);
  const [submitting, setSubmitting] = useState(false);

  // リアルタイムで入力値変更処理
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
  ) => {
    // 変更があったフォーム要素からname, valueを取得
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };


  // バリデーション
  const validate = () => {
    if (!form.title.trim()) return "タイトル";
  };

  // 送信処理
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const error = validate();
    if (error) {
      alert(`以下の必須項目が入力されていません：\n・${error}`);
      return;
    }
    setSubmitting(true);
    //送信するデータ（formの中身）をまとめる
    const sendData = {
      title: form.title,
      is_completed: form.is_completed,
    };

    console.log(sendData); 

    try {
      // サーバーAPIにPOSTリクエストでデータ送信
      const res = await fetch("http://localhost:80/api/todos", {
        method: "POST",
        headers: { "Content-Type": "application/json" }, // JSON形式で送る
        body: JSON.stringify(sendData), // データをJSON文字列に変換して送信
      });
      if (res.ok) {
        // 成功したら「送信完了」のアラートを表示し、フォームを初期状態に戻す
        alert("お問い合わせを送信しました！");
        setForm(defaultForm);
      } else {
        // サーバーからエラーが返ってきた場合
        alert("送信に失敗しました");
      }
    } catch {
      // 通信エラーなどで失敗した場合
      alert("送信に失敗しました");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <form id="contactForm" onSubmit={handleSubmit}>
        <h1>Practice TODO List</h1>
        <p></p>
        <table>
          <tbody>
            <tr>
              <th>
                <input
                  type="text"
                  name="title"
                  className="long-input"
                  placeholder="todoを入力してください"
                  maxLength={50}
                  value={form.title}
                  onChange={handleChange}
                />
              </th>
              <td>
                <input
                  type="submit"
                  value={submitting ? "送信中..." : "送信"}
                  className="submit"
                  disabled={submitting}
                />
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </div>
  );
}
