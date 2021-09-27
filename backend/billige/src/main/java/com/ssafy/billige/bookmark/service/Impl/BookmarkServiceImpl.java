package com.ssafy.billige.bookmark.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.billige.bookmark.domain.Bookmark;
import com.ssafy.billige.bookmark.domain.BookmarkId;
import com.ssafy.billige.bookmark.dto.response.BookmarkItemResponse;
import com.ssafy.billige.bookmark.repository.BookmarkRepository;
import com.ssafy.billige.bookmark.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

	private final BookmarkRepository bookmarkRepository;

	@Override
	public void addBookmark(Long uid, Long itemId) {
		BookmarkId bookmarkId = BookmarkId.builder()
			.uid(uid)
			.itemId(itemId)
			.build();
		Bookmark bookmark = Bookmark.builder()
			.bookmarkId(bookmarkId)
			.build();
		bookmarkRepository.save(bookmark);
	}

	@Override
	public void cancelBookmark(Long uid, Long itemId) {
		BookmarkId bookmarkId = BookmarkId.builder()
			.uid(uid)
			.itemId(itemId)
			.build();
		bookmarkRepository.deleteById(bookmarkId);
	}

	@Override
	public List<BookmarkItemResponse> getBookmarkItems(Long uid) {
		List<Bookmark> bookmarks = bookmarkRepository.findAllByBookmarkId_Uid(uid);

		return null;
	}
}
