//
//  Requests.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import RxNetworkApiClient


extension ExtendedApiRequest {

   
    // MARK: - File

    static func uploadImages(_ images: [Data]) -> ExtendedApiRequest {
        var data = [UploadFile]()
        for image in images {
            data.append(UploadFile("photos", image, "multipart/form-data"))
        }
        return extendedRequest(path: "/api/uploadImages",
                               method: .post,
                               files: data)

    }
    
    static func uploadSingleImage(_ image: Data) -> ExtendedApiRequest {
        return extendedRequest(path: "/api/uploadSingleImage",
                               method: .post,
                               files: [UploadFile("file" , image, "multipart/form-data")])
    }
    


}

