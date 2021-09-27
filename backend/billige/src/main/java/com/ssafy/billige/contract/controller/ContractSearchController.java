package com.ssafy.billige.contract.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.billige.contract.service.ContractSearchService;
import com.ssafy.billige.utils.StringUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = StringUtils.CONTRACT)
@RequiredArgsConstructor
public class ContractSearchController {

	private final ContractSearchService contractSearchService;

	@GetMapping("/rent/{uid}")
	public ResponseEntity<?> rentItems(@PathVariable("uid") Long uid) {
		return ResponseEntity.ok().body(contractSearchService.myContracts(uid));
	}

	@GetMapping("/my")
	public ResponseEntity<?> myItems(@RequestParam("ownerId") Long ownerId, @RequestParam("itemId") Long itemId) {
		return ResponseEntity.ok().body(contractSearchService.myItemContracts(ownerId, itemId));
	}
}