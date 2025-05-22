// Inquiryエンティティ（お問い合わせ本体）用のリポジトリインターフェース
// Spring Data JPAのJpaRepositoryを継承することで、
// 保存・検索・削除・一覧取得などのDB操作を自動で利用可能になる
package com.example.api.repository;
import com.example.api.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
// JpaRepository<Todo, Long>を継承
// 第1引数：エンティティの型（Todo）
// 第2引数：主キーの型（Long）
public interface TodoRepository extends JpaRepository<Todo, Long> {}