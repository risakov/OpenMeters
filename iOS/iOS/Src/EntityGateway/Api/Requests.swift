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

    static func uploadFile(_ file: UploadFile) -> ExtendedApiRequest {
        return extendedRequest(path: "/files",
                               method: .post,
                               files: [file])
    }


}

