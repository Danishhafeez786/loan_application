package com.loanapproval.model;

import lombok.Data;
import org.w3c.dom.DocumentType;

@Data
public class UploadDocumentRequest {

    private DocumentType documentType;
}