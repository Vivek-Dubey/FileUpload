package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="file_upload_details")
@AllArgsConstructor
@Setter
@Builder
@Getter
@NoArgsConstructor
public class FileUploadEntity {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="created_on",nullable = true)
	private Timestamp createdOn;
	
	@Column(name="file_name",nullable = false)
	private String fileName;
	
	@Column(name="file_location",nullable = false)
	private String fileLocation;
	
	@Column(name="owner_id",nullable = true)
	private Long ownerId;

}
